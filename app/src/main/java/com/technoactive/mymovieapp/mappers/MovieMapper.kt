package com.technoactive.mymovieapp.mappers

import com.technoactive.mymovieapp.entities.MovieEntity
import com.technoactive.mymovieapp.model.MovieItem

object MovieMapper {

    fun MovieEntity.toUi() = MovieItem(
        id = imdbId,
        movieTitle = title,
        movieYear = year,
        moviePoster = poster
    )

    fun MovieItem.toEntity() = MovieEntity(
        imdbId = id,
        title = movieTitle,
        year = movieYear,
        poster = moviePoster
    )
}