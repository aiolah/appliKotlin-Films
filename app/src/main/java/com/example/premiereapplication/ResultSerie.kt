package com.example.premiereapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResultSerie(viewModel: MainViewModel, id: String?) {
    val serie by viewModel.serie.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) { viewModel.getSingleSerie(id) }

    var date = serie.first_air_date

    // Text("Voici un film qui a pour id : ${id}")

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        item(span = { GridItemSpan(2) }) { TitreSerie(serie.name) }

        item(span = { GridItemSpan(2) }) { ImageHorizontale(serie.backdrop_path) }

        item(span = { GridItemSpan(2) }) { InfosFilm(serie.poster_path, date, serie.genres) }

        item(span = { GridItemSpan(2) }) { Synopsis(serie.overview) }

        item(span = { GridItemSpan(2) }) { Text("Sociétés de production", fontWeight = FontWeight.Bold, fontSize = 25.sp, modifier = Modifier.padding(15.dp)) }

        items(serie.production_companies) { company ->
            Company(company)
        }
    }
}