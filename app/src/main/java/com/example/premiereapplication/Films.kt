package com.example.premiereapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.forEach

@Composable
fun Films(viewModel: MainViewModel) {
    // Observation dans un composant compose, transforme le MutableStateFlow en une liste
    val movies by viewModel.movies.collectAsStateWithLifecycle()

    // Pour n'appeler viewModel.getMovies() qu'une seule fois = première apparition du composant Films
    LaunchedEffect(key1 = true) { viewModel.getMovies() }

    Text(text = "Les films populaires de la semaine")
    
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(movies) { movie ->
            Card(modifier = Modifier
                .height(300.dp)
                .requiredHeight(300.dp)) {
                Column {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w780/${movie.poster_path}",
                        contentDescription = null,
                    )
                    Text(text = movie.title)
                }
            }
        }
    }

    // Affichage des titres des films
    /* Column() {
        for(movie in movies) {
            Row() {
                Text(text = movie.title)
            }
        }
    } */
}