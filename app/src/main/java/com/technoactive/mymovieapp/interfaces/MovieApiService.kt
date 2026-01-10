package com.technoactive.mymovieapp.interfaces

import com.technoactive.mymovieapp.model.MovieDetailsResponse
import com.technoactive.mymovieapp.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MovieApiService {

    @GET("/")
    suspend fun getMovies(
        @QueryMap params: HashMap<String, Any>
    ): MovieResponse

    @GET("/")
    suspend fun getMovieDetails(
        @QueryMap params: HashMap<String, Any>
    ): MovieDetailsResponse?
}