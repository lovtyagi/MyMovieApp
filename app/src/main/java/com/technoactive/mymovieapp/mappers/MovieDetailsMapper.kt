package com.technoactive.mymovieapp.mappers

import com.technoactive.mymovieapp.entities.MovieDetailsEntity
import com.technoactive.mymovieapp.model.MovieDetailsResponse

object MovieDetailsMapper {

    fun MovieDetailsEntity.toUi() = MovieDetailsResponse(
        id = imdbId,
        movieTitle = title,
        movieYear = year,
        moviePoster = poster,
        plot = plot
    )

    fun MovieDetailsResponse.toEntity() = MovieDetailsEntity(
        imdbId = id,
        title = movieTitle,
        year = movieYear,
        poster = moviePoster,
        plot = plot
    )
}