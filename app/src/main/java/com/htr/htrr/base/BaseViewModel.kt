package com.htr.htrr.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay

open class UiState

open class UiEffect

open class UiEvent

abstract class BaseViewModel<State : UiState, Effect : UiEffect, Event : UiEvent> : ViewModel() {

    protected val currentState: State
        get() = _state.value

    @Suppress("VariableNaming")
    protected val _state by lazy(LazyThreadSafetyMode.NONE) { MutableStateFlow(createInitialState()) }
    val state: StateFlow<State> by lazy(LazyThreadSafetyMode.NONE) { _state.asStateFlow() }

    @Suppress("VariableNaming")
    protected val _event: MutableSharedFlow<Event> = MutableSharedFlow(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val event: SharedFlow<Event> = _event.asSharedFlow()

    @Suppress("VariableNaming")
    private val _effect: Channel<Effect> = Channel(Channel.UNLIMITED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    // Job tracking for cancellation
    private val jobs = mutableMapOf<String, Job>()

    init {
        viewModelScope.launch {
            event.collect { handleEvent(it) }
        }
    }

    // Public API
    fun sendEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    fun sendEvents(vararg events: Event) {
        viewModelScope.launch {
            events.forEach { event ->
                _event.emit(event)
            }
        }
    }

    // Protected methods for ViewModel implementations
    protected fun setState(reduce: State.() -> State) {
        _state.value = currentState.reduce()
    }

    protected fun setState(newState: State) {
        _state.value = newState
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    protected fun sendEffects(vararg effects: Effect) {
        viewModelScope.launch {
            effects.forEach { effect ->
                _effect.send(effect)
            }
        }
    }

    protected inline fun launch(crossinline block: suspend () -> Unit): Job =
        viewModelScope.launch { block() }

    protected fun launchWithKey(key: String, block: suspend () -> Unit): Job {
        jobs[key]?.cancel()
        val job = viewModelScope.launch {
            try {
                block()
            } finally {
                jobs.remove(key)
            }
        }
        jobs[key] = job
        return job
    }

    protected fun cancelJob(key: String) {
        jobs[key]?.cancel()
        jobs.remove(key)
    }

    protected fun cancelAllJobs() {
        jobs.values.forEach { it.cancel() }
        jobs.clear()
    }

    protected fun executeAsync(
        operation: suspend () -> Unit,
        onSuccess: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onFinally: (() -> Unit)? = null
    ): Job = launch {
        try {
            operation()
            onSuccess?.invoke()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            onFinally?.invoke()
        }
    }

    protected fun <T> executeAsyncWithResult(
        operation: suspend () -> Result<T>,
        onSuccess: (T) -> Unit,
        onError: ((Result.Error) -> Unit)? = null,
        onFinally: (() -> Unit)? = null
    ): Job = launch {
        try {
            when (val result = operation()) {
                is Result.Success -> onSuccess(result.data as T)
                is Result.Error -> {
                    onError?.invoke(
                        Result.Error(
                            message = result.message,
                            code = result.code,
                            exception = result.exception
                        )
                    )
                }
            }
        } catch (e: CancellationException) {
            throw e // Re-throw cancellation
        } catch (e: Exception) {
            // Handle unexpected exceptions (network issues, parsing errors, etc.)
            onError?.invoke( Result.Error(e, null, e.message))
        } finally {
            onFinally?.invoke()
        }
    }

    protected fun executeWithRetry(
        maxRetries: Int = 3,
        delayMillis: Long = 1000,
        operation: suspend () -> Unit,
        onSuccess: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ): Job = launch {
        var lastException: Exception? = null
        repeat(maxRetries + 1) { attempt ->
            try {
                operation()
                onSuccess?.invoke()
                return@launch
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                lastException = e
                if (attempt < maxRetries) {
                    delay(delayMillis * (attempt + 1))
                }
            }
        }
        lastException?.let { onError?.invoke(it) }
    }

    protected fun <T> collectFlow(
        flow: Flow<T>,
        onCollect: (T) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    ): Job = launch {
        try {
            flow.collect { value ->
                onCollect(value)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    protected fun debounce(
        key: String,
        delayMillis: Long = 300,
        action: () -> Unit
    ) {
        launchWithKey("debounce_$key") {
            delay(delayMillis)
            action()
        }
    }

    protected fun throttle(
        key: String,
        intervalMillis: Long = 1000,
        action: () -> Unit
    ) {
        val throttleKey = "throttle_$key"
        if (!jobs.containsKey(throttleKey)) {
            action()
            launchWithKey(throttleKey) {
                delay(intervalMillis)
            }
        }
    }

    protected fun resetState() {
        setState(createInitialState())
    }

    protected fun <T> observeState(mapper: (State) -> T): Flow<T> =
        state.map(mapper).distinctUntilChanged()

    protected fun withState(condition: (State) -> Boolean, action: (State) -> Unit) {
        val current = currentState
        if (condition(current)) {
            action(current)
        }
    }

    abstract fun handleEvent(event: Event)

    protected abstract fun createInitialState(): State

    override fun onCleared() {
        super.onCleared()
        cancelAllJobs()
        _effect.close()
    }
}

// Example usage
private data class UserProfileState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val error: String? = null
) : UiState()

private sealed class UserProfileEffect : UiEffect() {
    data class ShowToast(val message: String) : UserProfileEffect()
    data class Navigate(val route: String) : UserProfileEffect()
    data class ShowDialog(val title: String, val message: String) : UserProfileEffect()
}

private sealed class UserProfileEvent : UiEvent() {
    data class LoadUser(val userId: String) : UserProfileEvent()
    data class UpdateUser(val user: User) : UserProfileEvent()
    data class SearchUsers(val query: String) : UserProfileEvent()
    object ToggleEditMode : UserProfileEvent()
    object Retry : UserProfileEvent()
}

private data class User(val id: String, val name: String, val email: String)

private class UserProfileViewModel : BaseViewModel<UserProfileState, UserProfileEffect, UserProfileEvent>() {

    override fun createInitialState() = UserProfileState()

    override fun handleEvent(event: UserProfileEvent) {
        when (event) {
            is UserProfileEvent.LoadUser -> loadUser(event.userId)
            is UserProfileEvent.UpdateUser -> updateUser(event.user)
            is UserProfileEvent.SearchUsers -> searchUsers(event.query)
            is UserProfileEvent.ToggleEditMode -> toggleEditMode()
            is UserProfileEvent.Retry -> retryLoadUser()
        }
    }

    private fun loadUser(userId: String) {
        executeAsync(
            operation = {
                setState { copy(isLoading = true, error = null) }
                delay(2000) // Simulate API call
                val user = User(userId, "John Doe", "john@example.com")
                setState { copy(user = user, isLoading = false) }
            },
            onError = { error ->
                setState { copy(error = error.message, isLoading = false) }
                sendEffect(UserProfileEffect.ShowToast("Failed to load user"))
            }
        )
    }

    private fun updateUser(user: User) {
        executeAsyncWithResult(
            operation = {
                setState { copy(isLoading = true) }
                delay(1000) // Simulate API call
                Result.Success(user) // Return updated user
            },
            onSuccess = { updatedUser ->
                setState { copy(user = updatedUser, isLoading = false, isEditing = false) }
                sendEffect(UserProfileEffect.ShowToast("User updated successfully"))
            },
            onError = {
                setState { copy(isLoading = false) }
                sendEffect(UserProfileEffect.ShowToast("Failed to update user"))
            }
        )
    }

    private fun searchUsers(query: String) {
        debounce("search", 500) {
            executeAsync(
                operation = {
                    delay(500) // Simulate search API
                    sendEffect(UserProfileEffect.ShowToast("Search completed for: $query"))
                }
            )
        }
    }

    private fun toggleEditMode() {
        setState { copy(isEditing = !isEditing) }
    }

    private fun retryLoadUser() {
        currentState.user?.let { user ->
            loadUser(user.id)
        }
    }
}