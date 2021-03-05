package com.interview.willyweathertest.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "NowPlayingMovie")
data class NowPlayingMovie(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Long,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String,

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overView: String,

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val voteAverage: Float,

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    val coverPhoto: String,


    @ColumnInfo(name = "runtime")
    @SerializedName("runtime")
    val runtime: Int = 0,

    @ColumnInfo(name="genres")
    @SerializedName("genres")
    val genres: List<Genres>?
)