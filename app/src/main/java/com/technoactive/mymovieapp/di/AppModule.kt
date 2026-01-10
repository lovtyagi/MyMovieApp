package com.technoactive.mymovieapp.di

import android.content.Context
import androidx.room.Room
import com.technoactive.mymovieapp.interfaces.MovieDao
import com.technoactive.mymovieapp.roomdb.AppDatabase
import com.technoactive.mymovieapp.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movies.db"
        ).build()

    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao =
        db.movieDao()

    @Provides
    @Singleton
    fun provideNetworkHelper(
        @ApplicationContext context: Context
    ): NetworkHelper = NetworkHelper(context)
}
