package com.interview.willyweathertest.data.remote

import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.data.remote.response.NowPlayingResponse
import com.interview.willyweathertest.data.remote.response.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHelper @Inject constructor(val apiService: ApiService) {

    suspend fun popularMovies(
        queryMap: HashMap<String, String>
    ) = apiService.popularMovies(queryMap)

    suspend fun nowPlayingMovies(
        queryMap: HashMap<String, String>
    ) = apiService.nowPlayingMovies(queryMap)

    suspend fun movieDetails(
        movieId: Long,
        queryMap: HashMap<String, String>
    ) = apiService.movieDetails(movieId, queryMap)
}