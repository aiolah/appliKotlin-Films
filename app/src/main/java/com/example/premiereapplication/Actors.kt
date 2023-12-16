package com.example.premiereapplication

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

@Composable
fun Actors(viewModel: MainViewModel, navController: NavHostController, view: String, numberColumns: Int) {
    val actors by viewModel.actors.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) { viewModel.getActors() }

    // Text(text = "Les acteurs populaires de la semaine")

    LazyVerticalGrid(columns = GridCells.Fixed(numberColumns)) {
        items(actors) { actor ->
            ActorCard(actor, navController, view)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorCard(actor: Person, navController: NavHostController, view: String) {
    ElevatedCard(
        onClick = { navController.navigate("actor/${actor.id}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(20.dp)
    ) {
        val customModifier = Modifier
            .takeIf { view == "paysage" }
            ?.then(Modifier.height(150.dp).align(Alignment.CenterHorizontally))
            ?: Modifier

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${actor.profile_path}",
            contentDescription = null,
            modifier = customModifier
        )
        Text(text = actor.name, modifier = Modifier.padding(7.dp), fontWeight = FontWeight.Bold)
    }
}