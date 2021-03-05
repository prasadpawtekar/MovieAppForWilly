package com.interview.willyweathertest.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.data.model.NowPlayingMovie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movies: List<Movie>): List<Long>

    @Query("DELETE FROM Movie")
    suspend fun deleteAllMovies(): Int

    @Query("SELECT * FROM Movie")
    suspend fun allMovies(): List<Movie>
}