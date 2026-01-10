package com.technoactive.mymovieapp.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.technoactive.mymovieapp.entities.MovieDetailsEntity
import com.technoactive.mymovieapp.entities.MovieEntity
import com.technoactive.mymovieapp.interfaces.MovieDao

@Database(
    entities = [MovieEntity::class, MovieDetailsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}