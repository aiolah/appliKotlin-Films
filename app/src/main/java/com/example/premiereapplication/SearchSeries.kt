package com.example.premiereapplication

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
fun SearchSeries(viewModel: MainViewModel, navController: NavHostController, query: String) {
    // Observation dans un composant compose, transforme le MutableStateFlow en une liste
    val series by viewModel.series.collectAsStateWithLifecycle()

    // Pour n'appeler viewModel.getMovies() qu'une seule fois = première apparition du composant Films
    LaunchedEffect(key1 = query)
    {
        viewModel.getSearchSeries(query)
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(series) { serie ->
            try {
                var date = serie.first_air_date
                if(date != "")
                {
                    var res = LocalDate.parse(date)
                    var dateFormattee = res.format(DateTimeFormatter.ofLocalizedDate((FormatStyle.SHORT)))

                    serie.first_air_date = dateFormattee
                }
            }
            catch(e: Exception) {
                Log.d("VAÏTI", "Erreur date")
            }

            ResultSerieCard(serie, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultSerieCard(serie: Serie, navController: NavHostController) {
    ElevatedCard(
        onClick = { navController.navigate("resultserie/${serie.id}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${serie.poster_path}",
            contentDescription = null,
        )
        Text(text = serie.name, modifier = Modifier.padding(7.dp), fontWeight = FontWeight.Bold)
        Text(text = serie.first_air_date, modifier = Modifier.padding(7.dp))
    }
}