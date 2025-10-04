package com.codebyyosry.samples.apps.feature.movie_list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.codebyyosry.samples.apps.core.designsystem.common.UiStatus
import com.codebyyosry.samples.apps.core.domain.mapper.Resource
import com.codebyyosry.samples.apps.core.domain.usecase.GetMoviesStreamUseCase
import com.codebyyosry.samples.apps.core.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesStreamUseCase: GetMoviesStreamUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // TODO: Step 3 - Define the type of data your UiState will hold on success.
    // For example, for a list of items, it would be List<YourItem>.
    // For a login screen, it might be Unit if no data is returned on success.
    private val _state = MutableStateFlow(MoviesUiState<String>())
    val state = _state.asStateFlow()
    // Paging flow from use case
    val moviesPagingData = getMoviesStreamUseCase()
        .cachedIn(viewModelScope)
    private val _effect = Channel<MoviesUiEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        // This block is for handling arguments passed during navigation.
        // TODO: Step 4 - If this screen receives arguments, uncomment and adapt the following lines.
        // val yourArgument = savedStateHandle.get<String>("yourArgumentKey")
        // if (yourArgument != null) {
        //     onEvent(MoviesUiEvent.LoadData(yourArgument))
        // } else {
        //     // Handle the case where the argument is missing, if necessary.
        // }
    }

    /**
     * The single entry point for all UI actions.
     */
    fun onEvent(event: MoviesUiEvent) {
        when (event) {
            is MoviesUiEvent.Refresh -> {
                // Refresh handled automatically in UI by LazyPagingItems.refresh()
            }

            is MoviesUiEvent.ToggleFavorite -> {
                viewModelScope.launch {
                    Log.d("MoviesVM", "ToggleFavorite called: ${event.movieId}, ${event.newState}")
                    val result = toggleFavoriteUseCase(event.movieId, event.newState)
                    when (result) {
                        is Resource.Success -> println()
                        else -> println()
                    }
                }
            }
        }
    }

    /**
     * Example function to handle data loading logic.
     */
    private fun loadData(argument: String) {
        viewModelScope.launch {
            _state.update { it.copy(status = UiStatus.Loading) }

            // TODO: Step 6 - Call your UseCase and handle the result.
            // This assumes your UseCase returns a Flow<Resource<T>>.
            /*
            yourUseCase(argument).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(operationStatus = UiStatus.Loading) }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                operationStatus = UiStatus.Success(result.data),
                                // You can also update other state properties here
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(operationStatus = UiStatus.Error(result.messageResId)) }
                        // Optionally, send an effect to show a toast or snackbar
                        sendEffect(MoviesUiEffect.ShowSnackbar(result.messageResId))
                    }
                }
            }
            */
        }
    }
    
    /**
     * Helper function to send a one-time side effect to the UI.
     */
    private fun sendEffect(effect: MoviesUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}