package com.technoactive.mymovieapp.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(paddingValues: PaddingValues) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "movie_list") {

        composable("movie_list") {
            MoviesScreen(
                onMovieClick = { movieId ->
                    navController.navigate("movie_detail/$movieId")
                }
            )
        }

        composable(
            route = "movie_detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: return@composable
            MovieDetailScreen {
                navController.popBackStack()
            }
        }
    }
}
