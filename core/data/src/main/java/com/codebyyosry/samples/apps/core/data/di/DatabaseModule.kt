package com.codebyyosry.samples.apps.core.data.di

import android.content.Context
import androidx.room.Room
import com.codebyyosry.samples.apps.core.data.local.DemoDatabase
import com.codebyyosry.samples.apps.core.data.local.MoviesLocalDataSource
import com.codebyyosry.samples.apps.core.data.local.dao.MovieDao
import com.codebyyosry.samples.apps.core.data.local.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DemoDatabase {
        return Room.databaseBuilder(
            context.applicationContext, DemoDatabase::class.java, "demo_database"
        ).fallbackToDestructiveMigration(true).build()

    }

    @Provides
    fun provideMovieDao(database: DemoDatabase) = database.movieDao()

    @Provides
    fun provideRemoteKeysDao(database: DemoDatabase) = database.remoteKeysDao()

    @Provides
    @Singleton
    fun provideMoviesLocalDataSource(
        db: DemoDatabase,
        movieDao: MovieDao,
        remoteKeysDao: RemoteKeysDao
    ) = MoviesLocalDataSource(db, movieDao, remoteKeysDao)
}