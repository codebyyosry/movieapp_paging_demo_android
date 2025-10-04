package com.codebyyosry.samples.apps.feature.movie_list

import androidx.annotation.StringRes
import androidx.paging.compose.LazyPagingItems
import com.codebyyosry.samples.apps.core.designsystem.common.UiStatus
import com.codebyyosry.samples.apps.core.domain.model.Movie

// TODO: Adjust this import to your actual UiStatus location if needed

/**
 * Defines all possible one-time side effects that can be triggered from the ViewModel.
 * These are events that the UI should react to but not store as state.
 */
sealed interface MoviesUiEffect {
    data class NavigateTo(val route: String) : MoviesUiEffect
    data class ShowSnackbarMessage(@StringRes val messageResId: Int) : MoviesUiEffect // Example}
}

/**
 * Defines all user actions that can be triggered from the UI.
 */
sealed interface MoviesUiEvent {
    object Refresh : MoviesUiEvent
    data class ToggleFavorite(val movieId: Long, val newState: Boolean,  val moviesPagingItems: LazyPagingItems<Movie>? = null) : MoviesUiEvent
}

/**
 * Represents the complete visual state of the screen.
 * It is a pure data class, making it easy to test and preview.
 *
 * @param T The type of data this state will hold upon success.
 * use Unit if there no data in Success, or you have explicit attributes like email, passwrod etc ...
 */
data class MoviesUiState<T>(
    val status: UiStatus<T> = UiStatus.Loading
    // Note: We removed the redundant 'data' property.
    // The data will be accessed via 'state.status.data' when the status is Success.
)