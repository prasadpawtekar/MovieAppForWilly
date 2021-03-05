package com.interview.willyweathertest.data.local

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.data.model.NowPlayingMovie
import javax.inject.Inject

class LocalDbHelper @Inject constructor(
    val nowPlayingMovieDao: NowPlayingMovieDao,
    val movieDao: MovieDao
) {
    suspend fun saveMovies(movies: List<Movie>) = movieDao.save(movies)

    suspend fun deleteAllMovies() = movieDao.deleteAllMovies()

    suspend fun allMovies() = movieDao.allMovies()

    suspend fun saveNowPlayingMovies(movies: List<NowPlayingMovie>) = nowPlayingMovieDao.save(movies)


    suspend fun deleteAllNowPlayingMovie() = nowPlayingMovieDao.deleteAllNowPlayingMovie()

    suspend fun nowPlayingMovies() = nowPlayingMovieDao.nowPlayingMovies()
}