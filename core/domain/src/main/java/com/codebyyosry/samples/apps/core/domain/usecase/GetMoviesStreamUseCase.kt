package com.codebyyosry.samples.apps.core.domain.usecase

import com.codebyyosry.samples.apps.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesStreamUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getPopularMoviesStream()
}