package com.codebyyosry.samples.apps.core.domain.mapper // Or domain.util, domain.model

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null // Or a more specific ErrorType/Throwable
) {
    /**
     * Represents a successful outcome.
     * @param data The retrieved data.
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Represents an error outcome.
     * @param message A message describing the error.
     * @param data Optional: data that might have been partially loaded or cached.
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    /**
     * Represents a loading state.
     * @param data Optional: stale data that can be shown while loading.
     */
    class Loading<T>(data: T? = null) : Resource<T>(data)
}