package com.technoactive.mymovieapp.repos

import com.technoactive.mymovieapp.interfaces.MovieApiService
import com.technoactive.mymovieapp.interfaces.MovieDao
import com.technoactive.mymovieapp.mappers.MovieDetailsMapper.toEntity
import com.technoactive.mymovieapp.mappers.MovieDetailsMapper.toUi
import com.technoactive.mymovieapp.mappers.MovieMapper.toEntity
import com.technoactive.mymovieapp.mappers.MovieMapper.toUi
import com.technoactive.mymovieapp.model.MovieDetailsResponse
import com.technoactive.mymovieapp.model.MovieItem
import com.technoactive.mymovieapp.utils.NetworkHelper
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApiService,
    private val dao: MovieDao,
    private val networkHelper: NetworkHelper
) {
    suspend fun getMovies(params: HashMap<String, Any>): List<MovieItem> {
        if (!networkHelper.isConnected()) {
            val local = dao.getAllMovies()
            return if (local.isEmpty()) {
                emptyList()
            } else {
                local.map { it.toUi() }
            }
        }
        val remote = api.getMovies(params)
        if (!remote.movieList.isNullOrEmpty()) {
            dao.insertMovies(remote.movieList.map { it.toEntity() })
        }
        return dao.getAllMovies().map { it.toUi() }
    }

    suspend fun searchMovies(query: String): List<MovieItem> {
        val localResult = dao.searchMovies(query)
        if (localResult.isNotEmpty()) {
            return localResult.map { it.toUi() }
        }
        return emptyList()
    }

    suspend fun getMovieDetails(params: HashMap<String, Any>): MovieDetailsResponse? {
        val title = params["i"] as String
        if (!networkHelper.isConnected()) {
            val local = dao.getMovieDetails(title)
            return if (local == null) {
                null
            } else {
                local.toUi()
            }
        }
        val remote = api.getMovieDetails(params)
        if (null != remote) {
            dao.insertMovieDetails(remote.toEntity())
        }
        return dao.getMovieDetails(title).toUi()
    }
}
