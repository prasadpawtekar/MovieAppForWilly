package com.interview.willyweathertest.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.interview.willyweathertest.data.model.NowPlayingMovie

@Dao
interface NowPlayingMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movies: List<NowPlayingMovie>): List<Long>

    @Query("DELETE FROM NowPlayingMovie")
    suspend fun deleteAllNowPlayingMovie(): Int

    @Query("SELECT * FROM NowPlayingMovie")
    suspend fun nowPlayingMovies(): List<NowPlayingMovie>
}