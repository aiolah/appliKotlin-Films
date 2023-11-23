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
import coil.compose.AsyncImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun TitreSerie(titreSerie: String) {
    Text(
        text = titreSerie,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(15.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfosSerie(imagePath: String, date: String, genres: List<Genre>) {
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
fun Company(company: ProductionCompany) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(20.dp)
        //.height(325.dp)
        //.requiredHeight(325.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${company.logo_path}",
            contentDescription = null,
        )
        Text(text = company.name, modifier = Modifier.padding(7.dp), fontWeight = FontWeight.Bold)
        Text(text = company.origin_country, modifier = Modifier.padding(7.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Serie(viewModel: MainViewModel, id: String?) {
    val serie by viewModel.serie.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) { viewModel.getSingleSerie(id) }

    var date = serie.first_air_date

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        item(span = { GridItemSpan(2) }) { TitreSerie(serie.name) }

        item(span = { GridItemSpan(2) }) { ImageHorizontale(serie.backdrop_path) }

        item(span = { GridItemSpan(2) }) { InfosSerie(serie.poster_path, date, serie.genres) }

        item(span = { GridItemSpan(2) }) { Synopsis(serie.overview) }

        item(span = { GridItemSpan(2) }) { Text("Sociétés de production", fontWeight = FontWeight.Bold, fontSize = 25.sp, modifier = Modifier.padding(15.dp)) }

        items(serie.production_companies) { company -> Company(company) }
    }
}