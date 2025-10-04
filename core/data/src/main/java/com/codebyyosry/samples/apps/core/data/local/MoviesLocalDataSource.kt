package com.codebyyosry.samples.apps.core.data.local

import androidx.paging.PagingSource
import com.codebyyosry.samples.apps.core.data.local.dao.MovieDao
import com.codebyyosry.samples.apps.core.data.local.dao.RemoteKeysDao
import com.codebyyosry.samples.apps.core.data.local.entity.MovieEntity
import com.codebyyosry.samples.apps.core.data.local.entity.RemoteKeys

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Local data source that wraps DAOs. The repository should depend on this abstraction
 * (instead of depending directly on DAOs), which helps with testing and architectural clarity.
 */
@Singleton
class MoviesLocalDataSource @Inject constructor(
    private val db: DemoDatabase,
    private val movieDao: MovieDao,
    private val remoteKeysDao: RemoteKeysDao
) {

    // Expose paging source factory for Pager
    fun pagingSourceFactory(): () -> PagingSource<Int, MovieEntity> = { movieDao.pagingSource() }

    // Observe small lists
    fun observeAll(): Flow<List<MovieEntity>> = movieDao.observeAll()

    // Basic DAO operations
    suspend fun insertAll(movies: List<MovieEntity>) = movieDao.insertAll(movies)

    suspend fun upsert(movie: MovieEntity) = movieDao.upsert(movie)

    suspend fun updateFavorite(movieId: Long, isFavorite: Boolean) =
        movieDao.updateFavorite(movieId, isFavorite, System.currentTimeMillis())

    suspend fun clearAll() = movieDao.clearAll()

    suspend fun replaceAll(movies: List<MovieEntity>) = movieDao.replaceAll(movies)

    // RemoteKeys helpers
    suspend fun remoteKeysForMovieId(movieId: Long): RemoteKeys? = remoteKeysDao.remoteKeysMovieId(movieId)

    suspend fun insertRemoteKeys(keys: List<RemoteKeys>) = remoteKeysDao.insertAll(keys)

    suspend fun clearRemoteKeys() = remoteKeysDao.clearRemoteKeys()

    // Provide DB instance for withTransaction when repository/mediator needs it
    fun getDatabase() = db
}
