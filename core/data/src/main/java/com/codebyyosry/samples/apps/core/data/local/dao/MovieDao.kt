package com.codebyyosry.samples.apps.core.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.codebyyosry.samples.apps.core.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY popularity DESC, updatedAt DESC")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies ORDER BY popularity DESC, updatedAt DESC")
    fun observeAll(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: MovieEntity)

    @Query("UPDATE movies SET isFavorite = :fav, updatedAt = :updatedAt WHERE id = :movieId")
    suspend fun updateFavorite(movieId: Long, fav: Boolean, updatedAt: Long = System.currentTimeMillis())

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovie(movieId: Long): MovieEntity

    @Query("DELETE FROM movies")
    suspend fun clearAll()

    @Transaction
    suspend fun replaceAll(items: List<MovieEntity>) {
        clearAll()
        // Using insertAll with REPLACE is fine, but we keep explicit transaction helper when needed.
        insertAll(items)
    }


}