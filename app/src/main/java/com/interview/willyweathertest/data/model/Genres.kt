package com.interview.willyweathertest.data.model
import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
) {
    override fun toString(): String {
        return name
    }
}