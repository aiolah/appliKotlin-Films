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
fun ResultFilm(viewModel: MainViewModel, id: String?) {
    val movie by viewModel.movie.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) { viewModel.getSingleMovie(id) }

    var date = movie.release_date

    // Text("Voici un film qui a pour id : ${id}")

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        item(span = { GridItemSpan(2) }) { TitreFilm(movie.title) }

        item(span = { GridItemSpan(2) }) { ImageHorizontale(movie.backdrop_path) }

        item(span = { GridItemSpan(2) }) { InfosFilm(movie.poster_path, date, movie.genres) }

        item(span = { GridItemSpan(2) }) { Synopsis(movie.overview) }

        item(span = { GridItemSpan(2) }) { Text("Casting", fontWeight = FontWeight.Bold, fontSize = 25.sp, modifier = Modifier.padding(15.dp)) }

        items(movie.credits.cast) { actor ->
            Actor(actor)
        }
    }
}