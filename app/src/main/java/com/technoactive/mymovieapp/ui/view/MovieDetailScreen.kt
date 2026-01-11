package com.technoactive.mymovieapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.technoactive.mymovieapp.model.DataState
import com.technoactive.mymovieapp.model.MovieDetailsResponse
import com.technoactive.mymovieapp.viewmodels.MovieDetailsViewModel

@Composable
fun MovieDetailScreen(viewModel: MovieDetailsViewModel = hiltViewModel(), onBackPress:() -> Unit) {
    val data by viewModel.movieDetailsState.collectAsStateWithLifecycle()
    when(data) {
        is DataState.Error -> {
            val message = (data as DataState.Error).message
            ErrorUI(message)
        }
        DataState.Loading -> {
            LoaderUI()
        }
        is DataState.Success<*> -> {
            val movieDetails = (data as DataState.Success<MovieDetailsResponse>).data
            MovieDetailsItem(movieDetails, onBackPress)
        }
    }
}

@Composable
private fun MovieDetailsItem(data: MovieDetailsResponse, onBackClick:()-> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White).systemBarsPadding()) {
        Box(modifier = Modifier.padding(20.dp).clickable(interactionSource = MutableInteractionSource()){
            onBackClick()
        }){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 10.dp)) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .height(360.dp)
                ) {

                    AsyncImage(
                        model = data.moviePoster,
                        contentDescription = data.movieTitle,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = data.movieTitle,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = data.plot,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}
