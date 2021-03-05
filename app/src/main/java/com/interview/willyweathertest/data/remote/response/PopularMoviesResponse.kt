package com.interview.willyweathertest.data.remote.response

import com.google.gson.annotations.SerializedName
import com.interview.willyweathertest.data.model.Movie

data class PopularMoviesResponse (
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val movies: List<Movie>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)