package com.codebyyosry.samples.apps.core.domain.usecase

import com.codebyyosry.samples.apps.core.domain.repository.MovieRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Long, newState: Boolean) =
        repository.toggleFavorite(movieId, newState)
}