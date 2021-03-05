package com.interview.willyweathertest.data.repository

import android.content.Context
import android.util.Log
import com.interview.willyweathertest.common.Utils
import com.interview.willyweathertest.data.local.LocalDbHelper
import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.data.model.NowPlayingMovie
import com.interview.willyweathertest.data.remote.ApiHelper
import com.interview.willyweathertest.data.remote.response.NowPlayingResponse
import com.interview.willyweathertest.data.remote.response.PopularMoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(val context: Context, val apiHelper: ApiHelper, val localDbHelper: LocalDbHelper) {
    suspend fun popularMovies1(
        queryMap: HashMap<String, String>
    ) = apiHelper.popularMovies(queryMap)

    suspend fun popularMovies(
        queryMap: HashMap<String, String>
    ) : PopularMoviesResponse = withContext(Dispatchers.IO) {

        try{
            val response = apiHelper.popularMovies(queryMap)
            Log.e("okhttp", "Call made");
            response.movies?.let { saveMovies(it) }
            Log.e("okhttp", "Tried to save data");

            return@withContext response
        } catch(e: Exception) {
            e.printStackTrace()
            return@withContext PopularMoviesResponse(0, localDbHelper.allMovies(), 0, 0)
        }
    }

    suspend fun popularMoviesOld(
        queryMap: HashMap<String, String>
    ) : PopularMoviesResponse = withContext(Dispatchers.IO) {

        if(Utils.isNetworkAvailable(context)) {
            val response = apiHelper.popularMovies(queryMap)
            Log.e("okhttp", "Call made");
            response.movies?.let { saveMovies(it) }
            Log.e("okhttp", "Tried to save data");

            return@withContext response
        } else {
            return@withContext PopularMoviesResponse(0, localDbHelper.allMovies(), 0, 0)
        }
    }
    suspend fun nowPlayingMovies(
        queryMap: HashMap<String, String>
    ) : NowPlayingResponse = withContext(Dispatchers.IO) {

        if(Utils.isNetworkAvailable(context)) {
            val response = apiHelper.nowPlayingMovies(queryMap)
            response.movies?.let { saveNowPlayingMovies(it) }
            return@withContext response
        } else {
            return@withContext NowPlayingResponse(0, localDbHelper.nowPlayingMovies(), 0, 0)
        }
    }

    suspend fun nowPlayingMovies1(
        queryMap: HashMap<String, String>
    ) = apiHelper.nowPlayingMovies(queryMap)

    suspend fun movieDetails(
        movieId: Long,
        queryMap: HashMap<String, String>
    ) = apiHelper.movieDetails(movieId, queryMap)

    suspend fun saveNowPlayingMovies(
        nowPlayingMovie: List<NowPlayingMovie>
    ) = localDbHelper.saveNowPlayingMovies(nowPlayingMovie)

    suspend fun saveMovies(
        movies: List<Movie>
    ) = localDbHelper.saveMovies(movies)
}