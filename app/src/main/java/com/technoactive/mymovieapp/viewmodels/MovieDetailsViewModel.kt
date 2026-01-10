package com.technoactive.mymovieapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoactive.mymovieapp.model.DataState
import com.technoactive.mymovieapp.model.MovieDetailsResponse
import com.technoactive.mymovieapp.repos.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: String = savedStateHandle["movieId"] ?: throw IllegalStateException("movieId missing")

    private val _movieDetailsState = MutableStateFlow<DataState<MovieDetailsResponse>>(DataState.Loading)
    val movieDetailsState : StateFlow<DataState<MovieDetailsResponse>>
        get() = _movieDetailsState

    init {
        fetchMovieDetails(movieId)
    }

    fun fetchMovieDetails(imdbID: String) {
        viewModelScope.launch {
            _movieDetailsState.value = DataState.Loading
            try {
                val params : HashMap<String, Any> = hashMapOf()
                params["i"] = imdbID
                val data = repository.getMovieDetails(params)
                if (data == null) {
                    _movieDetailsState.value = DataState.Error("No Data Found")
                } else {
                    _movieDetailsState.value = DataState.Success(data)
                }
            } catch (e: Exception) {
                _movieDetailsState.value = DataState.Error("Something went wrong")
            }
        }
    }
}
