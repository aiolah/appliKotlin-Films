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
import androidx.navigation.NavHostController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResultFilm(viewModel: MainViewModel, id: String?, navController: NavHostController, view: String, numberColumns: Int) {
    val movie by viewModel.movie.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) { viewModel.getSingleMovie(id) }

    var date = movie.release_date

    // Text("Voici un film qui a pour id : ${id}")

    LazyVerticalGrid(columns = GridCells.Fixed(numberColumns)) {
        item(span = { GridItemSpan(numberColumns) }) { TitreFilm(movie.title) }

        item(span = { GridItemSpan(numberColumns) }) { ImageHorizontale(movie.backdrop_path) }

        item(span = { GridItemSpan(numberColumns) }) { InfosFilm(movie.poster_path, date, movie.genres) }

        item(span = { GridItemSpan(numberColumns) }) { Synopsis(movie.overview) }

        item(span = { GridItemSpan(numberColumns) }) { Text("Casting", fontWeight = FontWeight.Bold, fontSize = 25.sp, modifier = Modifier.padding(15.dp)) }

        items(movie.credits.cast) { actor ->
            ActorFromFilm(actor, navController, view)
        }
    }
}