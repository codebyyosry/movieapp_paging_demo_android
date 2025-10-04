package com.codebyyosry.samples.apps.moviesdemo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codebyyosry.samples.apps.feature.movie_list.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Set start destination
    val startDestination = Screen.Movies.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Movies Screen
        composable(route = Screen.Movies.route) {
            val moviesViewModel: MoviesViewModel = hiltViewModel()

            // Handle one-time UI effects (navigation, toast, etc.)
            LaunchedEffect(Unit) {
                moviesViewModel.effect.collect { effect ->
                    when (effect) {
                        is MoviesUiEffect.NavigateTo -> navController.navigate(effect.route)
                        else -> println("Unhandled effect: $effect")
                    }
                }
            }

            // Render MoviesScreen with state and events
            MoviesScreen(
                viewModel = moviesViewModel
            )
        }

    }
}
