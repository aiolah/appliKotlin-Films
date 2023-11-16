package com.example.premiereapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage

@Composable
fun TitreFilm(titreFilm: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = titreFilm,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(15.dp)
        )
    }
}

@Composable
fun ImageHorizontale(imagePath: String) {
    Row(horizontalArrangement = Arrangement.Center) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${imagePath}",
            contentDescription = null,
            modifier = Modifier
                .height(500.dp)
                .padding(15.dp)
        )
    }
}

@Composable
fun ImageVerticale(imagePath: String) {
    Row(horizontalArrangement = Arrangement.Center) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${imagePath}",
            contentDescription = null,
            modifier = Modifier.height(175.dp)
        )
    }
}

@Composable
fun Synopsis(text: String) {
    Row {
        Text(text = "Synopsis", fontWeight = FontWeight.Bold, fontSize = 25.sp, modifier = Modifier.padding(15.dp))
    }

    Row {
        Text(text = text, textAlign = TextAlign.Justify, modifier = Modifier.padding(15.dp))
    }
}

@Composable
fun Film(viewModel: MainViewModel, id: String?) {
    val movie by viewModel.movie.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) { viewModel.getSingleMovie(id) }

    // Text("Voici un film qui a pour id : ${id}")

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        TitreFilm(movie.title)

        ImageHorizontale(movie.backdrop_path)

        ImageVerticale(movie.poster_path)

        Synopsis(movie.overview)
    }
}