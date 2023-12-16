package com.example.premiereapplication

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Films(viewModel: MainViewModel, navController: NavHostController, view: String, numberColumns: Int) {
    // Observation dans un composant compose, transforme le MutableStateFlow en une liste
    val movies by viewModel.movies.collectAsStateWithLifecycle()

    // Pour n'appeler viewModel.getMovies() qu'une seule fois = première apparition du composant Films
    LaunchedEffect(key1 = true) { viewModel.getMovies() }

    // Text(text = "Les films populaires de la semaine")

    // Affichage des titres des films
    /* Column() {
        for(movie in movies) {
            Row() {
                Text(text = movie.title)
            }
        }
    } */

    LazyVerticalGrid(columns = GridCells.Fixed(numberColumns)) {
        items(movies) { movie ->
            try {
                var date = movie.release_date
                if(date != "")
                {
                    var res = LocalDate.parse(date)
                    var dateFormattee = res.format(DateTimeFormatter.ofLocalizedDate((FormatStyle.SHORT)))

                    movie.release_date = dateFormattee
                }
            }
            catch(e: Exception) {
                Log.d("VAÏTI", "Erreur date")
            }

            MovieCard(movie, navController, view)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(movie: Movie, navController: NavHostController, view: String) {
    ElevatedCard(
        onClick = { navController.navigate("film/${movie.id}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(20.dp)
        // .height(300.dp)
        // .requiredHeight(325.dp)
    ) {
        val customModifier = Modifier
            .takeIf { view == "paysage" }
            ?.then(Modifier.height(150.dp).align(Alignment.CenterHorizontally))
            ?: Modifier

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${movie.poster_path}",
            contentDescription = null,
            modifier = customModifier
        )
        Text(text = movie.title, modifier = Modifier.padding(7.dp), fontWeight = FontWeight.Bold)
        Text(text = movie.release_date, modifier = Modifier.padding(7.dp))
    }
}