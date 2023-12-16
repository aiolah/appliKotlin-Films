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
fun TitreFilm(titreFilm: String) {
    Text(
        text = titreFilm,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(15.dp)
    )
}

@Composable
fun ImageHorizontale(imagePath: String) {
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w780/${imagePath}",
        contentDescription = null,
        modifier = Modifier.padding(15.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfosFilm(imagePath: String, date: String, genres: List<Genre>) {
    var datePrint = date

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Column {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w780/${imagePath}",
                contentDescription = null,
                modifier = Modifier.height(175.dp)
            )
        }

        Column(modifier = Modifier.width(85.dp)) {
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

            Text(text = datePrint, modifier = Modifier.padding(7.dp))

            var text = ""
            for(genre in genres) {
                text = text + genre.name
                if(genre != genres.last())
                {
                    text = text + ", "
                }
            }
            Text(text = "${text}", fontStyle = FontStyle.Italic)
        }
    }
}

@Composable
fun Synopsis(text: String) {
    Column {
        Row {
            Text(text = "Synopsis", fontWeight = FontWeight.Bold, fontSize = 25.sp, modifier = Modifier.padding(15.dp))
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row {
            Text(text = text, textAlign = TextAlign.Justify, modifier = Modifier.padding(15.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorFromFilm(actor: Cast, navController: NavHostController, view: String) {
    ElevatedCard(
        onClick = { navController.navigate("actor/${actor.id}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(20.dp)
            //.height(325.dp)
            //.requiredHeight(325.dp)
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
        Text(text = actor.character, modifier = Modifier.padding(7.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Film(viewModel: MainViewModel, id: String?, navController: NavHostController, view: String, numberColumns: Int) {
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