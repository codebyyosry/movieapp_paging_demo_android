package com.codebyyosry.samples.apps.feature.movie_list

import MovieItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.codebyyosry.samples.apps.feature.movie_list.component.MovieItemSkeleton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesScreen(viewModel: MoviesViewModel) {
    val moviesPagingItems = viewModel.moviesPagingData.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Sticky header
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Popular Movies", fontSize = 28.sp, color = Color.White)
            }
        }

        // Handle initial loading (before any data)
        if (moviesPagingItems.loadState.refresh is LoadState.Loading) {
            items(6) { // show 6 skeletons
                MovieItemSkeleton()
            }
        } else {
            // Normal items
            items(
                count = moviesPagingItems.itemCount,
                key = moviesPagingItems.itemKey { it.id },
                contentType = moviesPagingItems.itemContentType { "MovieItem" }
            ) { index ->
                val movie = moviesPagingItems[index]
                if (movie != null) {
                    MovieItem(
                        movie = movie,
                        onFavoriteClicked = { newState ->
                            movie.isFavorite = !movie.isFavorite
                            viewModel.onEvent(MoviesUiEvent.ToggleFavorite(movie.id, newState, moviesPagingItems))

                        }
                    )
                } else {
                    MovieItemSkeleton()
                }
            }

            // Loading at the end
            when (moviesPagingItems.loadState.append) {
                is LoadState.Loading -> {
                    items(2) { MovieItemSkeleton() } // show 2 more skeletons at bottom
                }
                is LoadState.Error -> {
                    val e = moviesPagingItems.loadState.append as LoadState.Error
                    item { ErrorItem(message = e.error.localizedMessage ?: "Unknown error") }
                }
                else -> {}
            }

            // Error on refresh
            if (moviesPagingItems.loadState.refresh is LoadState.Error) {
                val e = moviesPagingItems.loadState.refresh as LoadState.Error
                item { ErrorItem(message = e.error.localizedMessage ?: "Unknown error") }
            }
        }
    }
}


@Composable
fun ErrorItem(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Text(text = "Error: $message", color = Color.Red)
    }
}