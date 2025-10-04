package com.codebyyosry.samples.apps.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.codebyyosry.samples.apps.core.data.local.MoviesLocalDataSource
import com.codebyyosry.samples.apps.core.data.mapper.toDomain
import com.codebyyosry.samples.apps.core.data.paging.MoviesRemoteMediator
import com.codebyyosry.samples.apps.core.data.remote.MoviesRemoteDataSource
import com.codebyyosry.samples.apps.core.domain.mapper.Resource
import com.codebyyosry.samples.apps.core.domain.model.Movie
import com.codebyyosry.samples.apps.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) : MovieRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularMoviesStream(): Flow<PagingData<Movie>> {
        // Use the localDataSource's pagingSourceFactory
        val pagingSourceFactory = localDataSource.pagingSourceFactory()

        return Pager(
            config = PagingConfig(
                pageSize = 20,           // number of items per page
                prefetchDistance = 5,    // load items ahead
                enablePlaceholders = false
            ),
            remoteMediator = MoviesRemoteMediator(
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .map { pagingData ->
                // Map MovieEntity -> Domain Movie
                pagingData.map { it.toDomain() }
            }

    }

    override suspend fun toggleFavorite(movieId: Long, newState: Boolean): Resource<Unit> {
        return try {
            localDataSource.updateFavorite(movieId, newState)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to update favorite: ${e.message}")
        }
    }
}