package com.htr.htrr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.htr.htrr.base.BaseViewModel
import com.htr.htrr.domain.repository.UserRepository
import com.htr.htrr.presentation.contract.HomeEffect
import com.htr.htrr.presentation.contract.HomeEvent
import com.htr.htrr.presentation.contract.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val userRepository: UserRepository
) : BaseViewModel<HomeScreenState, HomeEffect, HomeEvent>() {
    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadUser -> loadUser()
        }
    }

    override fun createInitialState(): HomeScreenState = HomeScreenState()

    private fun loadUser() {
        executeAsyncWithResult(
            operation = {
                userRepository.getUser()
            },
            onSuccess = { it ->
                setState {
                    copy(user = it)
                }
            },
            onError = {
                setState {
                    copy(error = it.message)
                }
            }
        )
    }

}