package com.codebyyosry.samples.apps.core.data.remote


import com.codebyyosry.samples.apps.core.data.remote.api.MoviesApiService
import com.codebyyosry.samples.apps.core.data.remote.dto.MovieDto
import com.codebyyosry.samples.apps.core.domain.mapper.Resource
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSource @Inject constructor(
    private val apiService: MoviesApiService
) {
    suspend fun getPopularMovies(page: Int): Resource<List<MovieDto>> {
        return try {
            val response = apiService.getPopularMovies(page)
            if (response.isSuccessful) {
                val movies = response.body()?.results ?: emptyList()
                Resource.Success(movies)
            } else {
                Resource.Error("HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    /**
     * Paging source fetch (returns raw list of MovieDto for RemoteMediator)
     */
    suspend fun getPopularMoviesRaw(page: Int): List<MovieDto> =
        apiService.getPopularMovies(page).body()?.results ?: emptyList()
}