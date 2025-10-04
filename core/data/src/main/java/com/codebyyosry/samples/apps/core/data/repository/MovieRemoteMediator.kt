package com.codebyyosry.samples.apps.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.codebyyosry.samples.apps.core.data.local.MoviesLocalDataSource
import com.codebyyosry.samples.apps.core.data.local.entity.MovieEntity
import com.codebyyosry.samples.apps.core.data.local.entity.RemoteKeys
import com.codebyyosry.samples.apps.core.data.mapper.toEntity
import com.codebyyosry.samples.apps.core.data.remote.MoviesRemoteDataSource

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        try {
            // Determine which page to load
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    val firstItem = state.firstItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    val remoteKeys = localDataSource.remoteKeysForMovieId(firstItem.id)
                    remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    val remoteKeys = localDataSource.remoteKeysForMovieId(lastItem.id)
                    remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            // Fetch movies from API
            val movies = remoteDataSource.getPopularMoviesRaw(page)
            val endOfPaginationReached = movies.isEmpty()

            // Perform DB operations in a single transaction
            localDataSource.getDatabase().withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localDataSource.clearAll()
                    localDataSource.clearRemoteKeys()
                }

                // Create RemoteKeys for each movie
                val keys = movies.map { movieDto ->
                    RemoteKeys(
                        movieId = movieDto.id, // ensure types match
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (endOfPaginationReached) null else page + 1
                    )
                }

                // Insert keys and movies using localDataSource
                localDataSource.insertRemoteKeys(keys)
                localDataSource.insertAll(movies.map { it.toEntity() })
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}
