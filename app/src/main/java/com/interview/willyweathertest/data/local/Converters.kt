package com.interview.willyweathertest.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.interview.willyweathertest.data.model.Genres
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Converters {
    @TypeConverter
    fun genresToJsonString(genresList: List<Genres>?) : String {
        if(genresList==null) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(genresList)
    }

    @TypeConverter
    fun jsonStringToGenres(genresJson: String) : List<Genres>? {
        if(genresJson == "") return null

        val gson = Gson()

        return gson.fromJson(genresJson, Array<Genres>::class.java).asList()
    }

}