package com.technoactive.mymovieapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviesdetails")
data class MovieDetailsEntity(
    @PrimaryKey
    val imdbId: String,
    val title: String,
    val year: String,
    val poster: String,
    val plot: String
)
