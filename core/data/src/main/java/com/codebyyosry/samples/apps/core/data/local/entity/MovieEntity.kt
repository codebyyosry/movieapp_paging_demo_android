package com.codebyyosry.samples.apps.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Long,
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val popularity: Double?,
    val isFavorite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)