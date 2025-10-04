package com.codebyyosry.samples.apps.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codebyyosry.samples.apps.core.data.local.dao.MovieDao
import com.codebyyosry.samples.apps.core.data.local.dao.RemoteKeysDao
import com.codebyyosry.samples.apps.core.data.local.entity.MovieEntity
import com.codebyyosry.samples.apps.core.data.local.entity.RemoteKeys

@Database(
    entities = [MovieEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class DemoDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}