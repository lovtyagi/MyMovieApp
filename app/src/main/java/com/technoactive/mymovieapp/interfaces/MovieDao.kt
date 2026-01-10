package com.technoactive.mymovieapp.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technoactive.mymovieapp.entities.MovieDetailsEntity
import com.technoactive.mymovieapp.entities.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("""
        SELECT * FROM movies 
        WHERE title LIKE '%' || :query || '%'
    """)
    suspend fun searchMovies(query: String): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movies: MovieDetailsEntity)

    @Query("""
        SELECT * FROM moviesdetails 
        WHERE imdbId LIKE '%' || :query || '%'
    """)
    suspend fun getMovieDetails(query: String): MovieDetailsEntity
}
