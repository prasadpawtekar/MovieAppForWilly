package com.interview.willyweathertest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.data.model.NowPlayingMovie
import javax.inject.Singleton

@Singleton
@Database(entities = [Movie::class, NowPlayingMovie::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase(){
    abstract val nowPlayingMovieDao: NowPlayingMovieDao
    abstract val movieDao: MovieDao

    companion object {
        val migration_1_2: Migration = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        };

        @Volatile
        private var INSTANCE: MovieDatabase? = null
        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "MovieDatabase"
                    )/*.addMigrations(migration_1_2)*/.build()
                }
                return instance
            }
        }

    }
}