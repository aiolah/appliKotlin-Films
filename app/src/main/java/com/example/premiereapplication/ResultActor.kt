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
fun ResultActor(viewModel: MainViewModel, id: String?, navController: NavHostController) {
    val actor by viewModel.actor.collectAsStateWithLifecycle()
    val actorMovies by viewModel.actormovies.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true)
    {
        viewModel.getSingleActor(id)
        viewModel.getActorMovies(id)
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        item(span = { GridItemSpan(2) }) { NomActeur(actor.name) }

        item(span = { GridItemSpan(2) }) { ImageVerticale(actor.profile_path) }

        item(span = { GridItemSpan(2) }) { Biographie(actor.biography) }

        item(span = { GridItemSpan(2) }) { Text("Casting", fontWeight = FontWeight.Bold, fontSize = 25.sp, modifier = Modifier.padding(15.dp)) }

        items(actorMovies.cast) { movie ->
            CastCard(movie, navController)
        }
    }
}