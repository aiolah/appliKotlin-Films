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
fun Acteurs(viewModel: MainViewModel) {
    val actors by viewModel.actors.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) { viewModel.getActors() }

    Text(text = "Les acteurs populaires de la semaine")

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(actors) { actor ->
            ActorCard(actor)
        }
    }
}

@Composable
fun ActorCard(actor: Person) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(20.dp)
            .height(325.dp)
            .requiredHeight(325.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${actor.profile_path}",
            contentDescription = null,
        )
        Text(text = actor.name, modifier = Modifier.padding(7.dp), fontWeight = FontWeight.Bold)
    }
}