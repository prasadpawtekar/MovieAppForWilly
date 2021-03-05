package com.interview.willyweathertest.common
import java.text.SimpleDateFormat

object Constants {
    //external fun apiKey(): String

    val MOVIE_ID = "movieId"
    const val BASE_URL = "https://api.themoviedb.org/3/"

    val DEFAULT_QUERY_PARAMETER = HashMap<String, String>().also {
        it.put("api_key", "12c46aa19f287edbb74f31f510230176")
        it.put("language", "en-US")
        it.put("page", "undefined")
    }

    val dateFormat: SimpleDateFormat = SimpleDateFormat("EEE MM, yyyy")
}