package com.technoactive.mymovieapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoactive.mymovieapp.model.DataState
import com.technoactive.mymovieapp.model.MovieDetailsResponse
import com.technoactive.mymovieapp.model.MovieItem
import com.technoactive.mymovieapp.repos.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _movieListState = MutableStateFlow<DataState<List<MovieItem>>>(DataState.Loading)
    val movieListState : StateFlow<DataState<List<MovieItem>>>
        get() = _movieListState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    init {
        fetchMovie()
    }

    fun fetchMovie() {
        viewModelScope.launch {
            _movieListState.value = DataState.Loading
            try {
                val params : HashMap<String, Any> = hashMapOf()
                params["s"] = "batman"
                params["page"] = 1
                params["type"] = "movie"
                val data = repository.getMovies(params)
                if (data.isEmpty()) {
                    _movieListState.value = DataState.Error("No Data Found")
                } else {
                    _movieListState.value = DataState.Success(data)
                }
            } catch (e: Exception) {
                _movieListState.value = DataState.Error("Something went wrong")
            }
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            val result = repository.searchMovies(query)
            _movieListState.value =
                if (result.isEmpty())
                    DataState.Error("No Data Found")
                else
                    DataState.Success(result)
        }
    }
}

