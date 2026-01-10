package com.technoactive.mymovieapp.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.technoactive.mymovieapp.R
import com.technoactive.mymovieapp.model.DataState
import com.technoactive.mymovieapp.model.MovieItem
import com.technoactive.mymovieapp.viewmodels.MovieViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    onMovieClick: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp).systemBarsPadding()
    ) {
        val data by viewModel.movieListState.collectAsStateWithLifecycle()

        val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::search,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search movies") },
            singleLine = true
        )


        Spacer(modifier = Modifier.height(16.dp))

        when (data) {

            is DataState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is DataState.Error -> {
                val message = (data as DataState.Error).message
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = message)
                }
            }

            is DataState.Success<*> -> {
                val movies = (data as DataState.Success<List<MovieItem>>).data

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(movies) { movie ->
                        MovieGridItem(
                            movie = movie,
                            onClick = { onMovieClick(movie.id) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MovieGridItem(
    movie: MovieItem,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxWidth()
        .heightIn(max = 250.dp).clickable(interactionSource = MutableInteractionSource()){
            onClick()
        }) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.moviePoster)
                    .crossfade(true)
                    .build(),
                contentDescription = movie.movieTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(172.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_placeholder_image),
                error = painterResource(R.drawable.ic_placeholder_image)
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = movie.movieTitle,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


