package com.example.premiereapplication

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    // Requêtes API pour les films
    @GET("trending/movie/week")
    suspend fun lastmovies(@Query("api_key") api_key: String, @Query("language") language:  String): TrendingMovieWeek
    @GET("movie/{id}")
    suspend fun singlemovie(@Path("id") id: String, @Query("api_key") api_key: String, @Query("append_to_response") append_to_response: String, @Query("language") language: String): SingleMovie
    @GET("search/movie")
    suspend fun searchmovies(@Query("api_key") api_key: String, @Query("include_adult") include_adults: String, @Query("page") page: String, @Query("query") query: String): TrendingMovieWeek

    // Requêtes API pour les séries
    @GET("trending/tv/week")
    suspend fun lastseries(@Query("api_key") api_key: String, @Query("language") language:  String): TrendingTvWeek
    @GET("tv/{id}")
    suspend fun singleserie(@Path("id") id: String, @Query("api_key") api_key: String, @Query("language") language: String): SingleSerie
    @GET("search/tv")
    suspend fun searchseries(@Query("api_key") api_key: String, @Query("include_adult") include_adults: String, @Query("page") page: String, @Query("query") query: String): TrendingTvWeek

    // Requêtes API pour les acteurs
    @GET("trending/person/week")
    suspend fun lastactors(@Query("api_key") api_key: String): TrendingPersonWeek
    @GET("person/{id}")
    suspend fun singleactor(@Path("id") id: String, @Query("api_key") api_key: String, @Query("language") language: String): SingleActor
    @GET("person/{id}/movie_credits")
    suspend fun actormovies(@Path("id") id: String, @Query("api_key") api_key: String, @Query("language") language: String): ActorMovies
    @GET("search/person")
    suspend fun searchactors(@Query("api_key") api_key: String, @Query("include_adult") include_adults: String, @Query("page") page: String, @Query("query") query: String): TrendingPersonWeek
}