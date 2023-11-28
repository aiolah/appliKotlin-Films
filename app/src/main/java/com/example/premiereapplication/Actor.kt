package com.example.premiereapplication

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun NomActeur(nomActeur: String) {
    Text(
        text = nomActeur,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(15.dp)
    )
}

@Composable
fun ImageVerticale(imagePath: String) {
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w780/${imagePath}",
        contentDescription = null,
        modifier = Modifier.padding(15.dp)
    )
}

@Composable
fun Biographie(text: String) {
    Column {
        Row {
            Text(text = "Biographie", fontWeight = FontWeight.Bold, fontSize = 25.sp, modifier = Modifier.padding(15.dp))
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row {
            Text(text = text, textAlign = TextAlign.Justify, modifier = Modifier.padding(15.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastCard(movie: ActorCast, navController: NavHostController) {
    var datePrint = movie.release_date
    try {
        if(datePrint != "")
        {
            var res = LocalDate.parse(datePrint)
            var dateFormattee = res.format(DateTimeFormatter.ofLocalizedDate((FormatStyle.SHORT)))

            datePrint = dateFormattee
        }
    }
    catch(e: Exception) {
        Log.d("VAÏTI", "Erreur date")
    }

    ElevatedCard(
        onClick = { navController.navigate("film/${movie.id}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${movie.poster_path}",
            contentDescription = null,
        )
        Text(text = movie.title, modifier = Modifier.padding(7.dp), fontWeight = FontWeight.Bold)
        Text(text = "Rôle : " + movie.character, modifier = Modifier.padding(7.dp), fontStyle = FontStyle.Italic)
        Text(text = datePrint, modifier = Modifier.padding(7.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Actor(viewModel: MainViewModel, id: String?, navController: NavHostController) {
    val actor by viewModel.actor.collectAsStateWithLifecycle()
    val actorMovies by viewModel.actormovies.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
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