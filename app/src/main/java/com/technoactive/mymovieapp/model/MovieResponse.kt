package com.technoactive.mymovieapp.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Search")
    val movieList: List<MovieItem>?,
    @SerializedName("totalResults")
    val totalResults: String?,
    @SerializedName("Response")
    val response: String
)


