package com.interview.willyweathertest.common

import java.lang.Exception

/*
sealed class Resource<out T>  {
    data class Success<T>(val data: T?) : Resource<T>()
    data class Error<T>(val data: T?, val exception: Exception) : Resource<T>()
    data class Loading<T>(val data: T?, val loadingMsg: String) : Resource<T>()
}*/
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

    }

}