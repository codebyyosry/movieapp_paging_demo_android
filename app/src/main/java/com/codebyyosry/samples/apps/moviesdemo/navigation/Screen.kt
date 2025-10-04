package com.codebyyosry.samples.apps.moviesdemo.navigation

sealed class Screen(val route: String) {
    object Movies : Screen("movie_screen")
}