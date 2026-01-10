package com.technoactive.mymovieapp.model

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("Title")
    val movieTitle: String,
    @SerializedName("Year")
    val movieYear: String,
    @SerializedName("imdbID")
    val id: String,
    @SerializedName("Poster")
    val moviePoster: String,
    @SerializedName("Plot")
    val plot: String
)


