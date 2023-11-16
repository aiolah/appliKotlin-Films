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

    var movies = MutableStateFlow<List<Movie>>(listOf())
    var series = MutableStateFlow<List<Serie>>(listOf())
    var actors = MutableStateFlow<List<Person>>(listOf())
    var movie = MutableStateFlow(SingleMovie())

    fun getMovies() {
        viewModelScope.launch {
            movies.value = api.lastmovies("73fbeeb046f41168a80509da0ee03c8c").results
        }
    }

    fun getSeries() {
        viewModelScope.launch {
            series.value = api.lastseries("73fbeeb046f41168a80509da0ee03c8c").results
        }
    }

    fun getActors() {
        viewModelScope.launch {
            actors.value = api.lastactors("73fbeeb046f41168a80509da0ee03c8c").results
        }
    }

    fun getSingleMovie(id: String?) {
        viewModelScope.launch {
            movie.value = api.singlemovie(id.toString(), "73fbeeb046f41168a80509da0ee03c8c", "credits", "fr")
        }
    }
}