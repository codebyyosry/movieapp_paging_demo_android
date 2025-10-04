package com.codebyyosry.samples.apps.core.data.mapper

import com.codebyyosry.samples.apps.core.data.local.entity.MovieEntity
import com.codebyyosry.samples.apps.core.data.remote.dto.MovieDto
import com.codebyyosry.samples.apps.core.domain.model.Movie


fun MovieDto.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    popularity = popularity ?: 0.0
)

fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title?: "",
    overview = overview?: "",
    posterPath = posterPath?: "",
    backdropPath = backdropPath?: "",
    releaseDate = releaseDate?: "",
    voteAverage = voteAverage?: 0.0,
    popularity = popularity?: 0.0,
    isFavorite = isFavorite,

)