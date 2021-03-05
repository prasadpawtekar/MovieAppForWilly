package com.interview.willyweathertest.ui.main

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.interview.willyweathertest.TestCoroutineRule
import com.interview.willyweathertest.common.Constants
import com.interview.willyweathertest.common.Resource
import com.interview.willyweathertest.common.Utils
import com.interview.willyweathertest.data.model.NowPlayingMovie
import com.interview.willyweathertest.data.remote.response.NowPlayingResponse
import com.interview.willyweathertest.data.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var nowPlayingResponseObserver: Observer<Resource<NowPlayingResponse>>

    @Mock
    private lateinit var repository: MainRepository

    var context: Context = mock(Context::class.java)


    /*@Test
    fun loadPlayingNow_returnsLoading() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(Resource.Loading(null, "Loading data. Please wait"))
                .`when`(repository)
                .nowPlayingMovies(HashMap<String, String>())

            val viewModel = MainViewModel(repository)

            viewModel.nowPlayingResponse.observeForever(nowPlayingResponseObserver)

            Mockito.verify(repository).nowPlayingMovies(HashMap<String, String>())

            Mockito.verify(nowPlayingResponseObserver)
                .onChanged(Resource.Loading(null, "Loading data. Please wait"))
            viewModel.nowPlayingResponse.removeObserver(nowPlayingResponseObserver)
        }
    }*/


    /*@Test
    fun loadPlayingNow_returnsSuccess() {
        testCoroutineRule.runBlockingTest {

            Mockito.doReturn(NowPlayingResponse(0, emptyList<NowPlayingMovie>(), 0, 0))
                .`when`(repository)
                .nowPlayingMovies(HashMap<String, String>())

            `when`(Utils.isNetworkAvailable(context)).thenReturn(true)

            val viewModel = MainViewModel(repository)
            viewModel.nowPlayingResponse.observeForever(nowPlayingResponseObserver)

            repository.nowPlayingMovies(HashMap<String, String>())

            Mockito.verify(repository).nowPlayingMovies(HashMap<String, String>())

            Mockito.verify(nowPlayingResponseObserver).onChanged(
                Resource.Success(
                    NowPlayingResponse(0, emptyList<NowPlayingMovie>(), 0, 0)
                )
            )
            viewModel.nowPlayingResponse.removeObserver(nowPlayingResponseObserver)
        }

    }*/

    @Test
    fun loadPlayingNow_returnsSuccess1() {
        testCoroutineRule.runBlockingTest {

            Mockito.doReturn(
                Resource.success(
                    NowPlayingResponse(
                        0,
                        emptyList<NowPlayingMovie>(),
                        0,
                        0
                    )
                )
            )
                .`when`(repository)
                .nowPlayingMovies1(Constants.DEFAULT_QUERY_PARAMETER)

            val viewModel = MainViewModel(repository)
            viewModel.nowPlayingResponse.observeForever(nowPlayingResponseObserver)

            viewModel.loadPlayingNowMovies()

            //Mockito.verify(nowPlayingResponseObserver).onChanged(Resource.loading(null))

            Mockito.verify(repository).nowPlayingMovies1(Constants.DEFAULT_QUERY_PARAMETER)

            Mockito.verify(nowPlayingResponseObserver).onChanged(
                Resource.success(
                    NowPlayingResponse(0, emptyList<NowPlayingMovie>(), 0, 0)
                )
            )
            viewModel.nowPlayingResponse.removeObserver(nowPlayingResponseObserver)
        }

    }

    @Test
    fun loadPlayingNow_returnsError() {

    }

}