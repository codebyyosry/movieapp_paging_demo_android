package com.codebyyosry.samples.apps.core.data.di

import com.codebyyosry.samples.apps.core.data.local.MoviesLocalDataSource
import com.codebyyosry.samples.apps.core.data.remote.MoviesRemoteDataSource
import com.codebyyosry.samples.apps.core.data.repository.MovieRepositoryImpl
import com.codebyyosry.samples.apps.core.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository
}