package com.codebyyosry.samples.apps.core.domain.repository

import androidx.paging.PagingData
import com.codebyyosry.samples.apps.core.domain.mapper.Resource
import com.codebyyosry.samples.apps.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMoviesStream(): Flow<PagingData<Movie>>
    suspend fun toggleFavorite(movieId: Long, newState: Boolean): Resource<Unit>
}