package com.codebyyosry.samples.apps.core.data.remote.api

import com.codebyyosry.samples.apps.core.data.remote.dto.MovieDto
import com.codebyyosry.samples.apps.core.data.remote.dto.MoviesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MoviesResponseDto>
}

