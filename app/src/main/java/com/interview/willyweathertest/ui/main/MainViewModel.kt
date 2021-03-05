package com.interview.willyweathertest.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.willyweathertest.common.Constants.DEFAULT_QUERY_PARAMETER
import com.interview.willyweathertest.common.Resource
import com.interview.willyweathertest.data.remote.response.NowPlayingResponse
import com.interview.willyweathertest.data.remote.response.PopularMoviesResponse
import com.interview.willyweathertest.data.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(val repository: MainRepository) : ViewModel() {

    var nowPlayingResponse = MutableLiveData<Resource<NowPlayingResponse>>()

    var popularMoviesResponse = MutableLiveData<Resource<PopularMoviesResponse>>()

    var pageNo = 0

    fun loadPlayingNowMovies() {

        viewModelScope.launch {
            val params = DEFAULT_QUERY_PARAMETER
            nowPlayingResponse.postValue(Resource.loading(null))
            try {
                nowPlayingResponse.postValue(Resource.success(repository.nowPlayingMovies1(params)))
            } catch (e: Exception) {
                nowPlayingResponse.postValue(Resource.error(e.toString(), null))
                e.printStackTrace()
            }
        }
    }

    fun loadMorePopularMovies() {

        viewModelScope.launch {
            pageNo++
            val params = DEFAULT_QUERY_PARAMETER
            params["page"] = pageNo.toString()
            popularMoviesResponse.postValue(Resource.loading(null))
            try {
                popularMoviesResponse.postValue(Resource.success(repository.popularMovies(params)))
            } catch (e: Exception) {
                pageNo--
                popularMoviesResponse.postValue(Resource.error(e.toString(), null))
            }
        }
    }
}