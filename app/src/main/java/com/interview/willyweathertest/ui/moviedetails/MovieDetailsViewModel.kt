package com.interview.willyweathertest.ui.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.willyweathertest.common.Constants
import com.interview.willyweathertest.common.Resource
import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.data.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(val repository: MainRepository): ViewModel()  {

    val movieDetails = MutableLiveData<Resource<Movie>>()
    val movieId = MutableLiveData<Long>()

    fun loadMovieDetails() {
        viewModelScope.launch {

            val params = Constants.DEFAULT_QUERY_PARAMETER
            movieDetails.postValue(Resource.loading(null))
            try {
                movieDetails.postValue(Resource.success(repository.movieDetails(movieId.value!!, params)))
            } catch (e: Exception) {
                movieDetails.postValue(Resource.error(e.toString(), null))
            }
        }
    }
}