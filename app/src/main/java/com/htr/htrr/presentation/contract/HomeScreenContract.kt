package com.htr.htrr.presentation.contract

import com.htr.htrr.base.UiEffect
import com.htr.htrr.base.UiEvent
import com.htr.htrr.base.UiState
import com.htr.htrr.domain.model.User

data class HomeScreenState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
): UiState()

sealed class HomeEffect : UiEffect() {
    object NavigateToDetails : HomeEffect()
}

sealed class HomeEvent : UiEvent() {
    object LoadUser : HomeEvent()
}