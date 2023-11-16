package com.example.premiereapplication

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage

@Composable
fun Series(viewModel: MainViewModel) {
    Text(text = "Les séries populaires de la semaine")

    val series by viewModel.series.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) { viewModel.getSeries() }

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(series) { serie ->
            SerieCard(serie)
        }
    }
}

@Composable
fun SerieCard(serie: Serie) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(20.dp)
            .height(325.dp)
            .requiredHeight(325.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${serie.poster_path}",
            contentDescription = null,
        )
        Text(text = serie.name, modifier = Modifier.padding(7.dp), fontWeight = FontWeight.Bold)
        Text(text = serie.first_air_date, modifier = Modifier.padding(7.dp))
    }
}