package com.codebyyosry.samples.apps.core.designsystem.common

import androidx.annotation.StringRes

sealed class UiStatus<out T> {
    object Idle : UiStatus<Nothing>()
    object Loading : UiStatus<Nothing>()
    data class Success<out T>(val data: T) : UiStatus<T>()
    data class Error(@StringRes val messageResId: Int) : UiStatus<Nothing>()
}


/*

when (uiState.status) {
    is UiStatus.Idle -> { /* show nothing */ }
    is UiStatus.Loading -> { /* show progress bar */ }
    is UiStatus.Success -> { /* show success UI */ }
    is UiStatus.Error -> {
        val message = (uiState.status as UiStatus.Error).message
        // show error message
    }
}
 */