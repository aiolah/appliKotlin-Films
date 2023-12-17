package com.example.premiereapplication

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();
    val api = retrofit.create(Api::class.java)

    // Liste de films, séries et acteurs
    var movies = MutableStateFlow<List<Movie>>(listOf())
    var series = MutableStateFlow<List<Serie>>(listOf())
    var actors = MutableStateFlow<List<Person>>(listOf())

    // Détails d'un film, d'une série et d'un acteur
    var movie = MutableStateFlow(SingleMovie())
    var serie = MutableStateFlow(SingleSerie())
    var actor = MutableStateFlow(SingleActor())

    // Films d'un acteur
    var actormovies = MutableStateFlow(ActorMovies())

    // Films
    fun getMovies() {
        viewModelScope.launch {
            movies.value = api.lastmovies("73fbeeb046f41168a80509da0ee03c8c", "fr").results
        }
    }

    fun getSingleMovie(id: String?) {
        viewModelScope.launch {
            movie.value = api.singlemovie(id.toString(), "73fbeeb046f41168a80509da0ee03c8c", "credits", "fr")
        }
    }

    fun getSearchMovies(query: String) {
        viewModelScope.launch {
            movies.value = api.searchmovies("73fbeeb046f41168a80509da0ee03c8c", "false", "1", query).results
        }
    }

    // Séries
    fun getSeries() {
        viewModelScope.launch {
            series.value = api.lastseries("73fbeeb046f41168a80509da0ee03c8c", "fr").results
        }
    }

    fun getSingleSerie(id: String?) {
        viewModelScope.launch {
            serie.value = api.singleserie(id.toString(), "73fbeeb046f41168a80509da0ee03c8c", "fr")
        }
    }

    fun getSearchSeries(query: String) {
        viewModelScope.launch {
            series.value = api.searchseries("73fbeeb046f41168a80509da0ee03c8c", "false", "1", query).results
        }
    }

    // Acteurs
    fun getActors() {
        viewModelScope.launch {
            actors.value = api.lastactors("73fbeeb046f41168a80509da0ee03c8c").results
        }
    }

    fun getSingleActor(id: String?) {
        viewModelScope.launch {
            actor.value = api.singleactor(id.toString(), "73fbeeb046f41168a80509da0ee03c8c", "fr")
        }
    }

    fun getActorMovies(id: String?) {
        viewModelScope.launch {
            actormovies.value = api.actormovies(id.toString(), "73fbeeb046f41168a80509da0ee03c8c", "fr")
        }
    }

    fun getSearchActors(query: String) {
        viewModelScope.launch {
            actors.value = api.searchactors("73fbeeb046f41168a80509da0ee03c8c", "false", "1", query).results
        }
    }
}