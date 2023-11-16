package com.example.premiereapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.forEach
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Films(viewModel: MainViewModel, navController: NavHostController) {
    // Observation dans un composant compose, transforme le MutableStateFlow en une liste
    val movies by viewModel.movies.collectAsStateWithLifecycle()

    // Pour n'appeler viewModel.getMovies() qu'une seule fois = première apparition du composant Films
    LaunchedEffect(key1 = true) { viewModel.getMovies() }

    Text(text = "Les films populaires de la semaine")
    
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(movies) { movie ->
            /* var date = movie.release_date
            var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            var text = date.format(formatter)
            var dateFormattee = LocalDate.parse(text, formatter).toString() */

            MovieCard(movie, navController)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(movie: Movie, navController: NavHostController) {
    ElevatedCard(
        onClick = { navController.navigate("film/${movie.id}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(20.dp)
            .height(325.dp)
            .requiredHeight(325.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${movie.poster_path}",
            contentDescription = null,
        )
        Text(text = movie.title, modifier = Modifier.padding(7.dp), fontWeight = FontWeight.Bold)
        Text(text = movie.release_date, modifier = Modifier.padding(7.dp))
    }
}