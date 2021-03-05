package com.interview.willyweathertest.data.remote

import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.data.remote.response.NowPlayingResponse
import com.interview.willyweathertest.data.remote.response.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {
    @GET("movie/popular")
    suspend fun popularMovies(
        @QueryMap queryMap: HashMap<String, String>
    ): PopularMoviesResponse

    @GET("movie/now_playing")
    suspend fun nowPlayingMovies(
        @QueryMap queryMap: HashMap<String, String>
    ): NowPlayingResponse

    @GET("movie/{movie_id}")
    suspend fun movieDetails(
        @Path("movie_id") movieId: Long,
        @QueryMap params: HashMap<String, String>
    ) : Movie

}