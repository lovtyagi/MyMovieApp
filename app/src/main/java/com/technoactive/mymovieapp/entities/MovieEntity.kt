package com.technoactive.mymovieapp.entities

import androidx.room.PrimaryKey

@androidx.room.Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val imdbId: String,
    val title: String,
    val year: String,
    val poster: String
)
