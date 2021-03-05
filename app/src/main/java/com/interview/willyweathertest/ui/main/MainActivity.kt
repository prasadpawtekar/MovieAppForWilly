package com.interview.willyweathertest.ui.main

import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.interview.willyweathertest.R
import com.interview.willyweathertest.common.Constants
import com.interview.willyweathertest.common.Resource
import com.interview.willyweathertest.common.Status
import com.interview.willyweathertest.common.Utils
import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.di.factory.ViewModelFactory
import com.interview.willyweathertest.ui.adapters.NowPlayingAdapter
import com.interview.willyweathertest.ui.adapters.PopularMovieAdapter
import com.interview.willyweathertest.ui.custom.setDivider
import com.interview.willyweathertest.ui.moviedetails.MovieDetailsActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var nowMoviePlayingAdapter: NowPlayingAdapter
    private var popularMovieAdapter = PopularMovieAdapter()

    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        context = this;
        initViews()

        setObservers()
        viewModel.loadPlayingNowMovies()

        f1()
        f5()
        f4()
        f3()
        f2()
    }


    private fun initScrollListeners() {
        loading = true
        popularMovieAdapter.addLoader(getString(R.string.loader_msg))

        rvPopular.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE && !loading) {
                    val position =
                        (rvPopular.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                    if (position == popularMovieAdapter.itemCount - 1) {
                        if (!Utils.isNetworkAvailable(context)) {
                            Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show()
                            return
                        }

                        loading = true
                        popularMovieAdapter.addLoader(getString(R.string.loader_msg))
                        rvPopular.scrollToPosition(popularMovieAdapter.itemCount - 1)
                        viewModel.loadMorePopularMovies()
                    }
                }
            }

        })
    }

    private fun initViews() {
        rvPlayingNow.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rvPopular.layoutManager = LinearLayoutManager(this)
        rvPopular.setDivider(R.drawable.divider)
        rvPopular.adapter = popularMovieAdapter
        popularMovieAdapter.setOnItemClicked { movie ->
            showMovieDetails(movie.id)
        }
    }


    private fun setObservers() {
        setPlayingNowMovieObserver()
        setPopularMovieObserver()
    }

    private fun setPlayingNowMovieObserver() {
        viewModel.nowPlayingResponse.observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    rvPlayingNow.visibility = View.GONE
                    playingNowLoader.visibility = View.VISIBLE
                    viewModel.loadMorePopularMovies()
                }

                Status.SUCCESS -> {
                    resource.data?.let {
                        rvPlayingNow.visibility = View.VISIBLE
                        playingNowLoader.visibility = View.GONE
                        nowMoviePlayingAdapter = NowPlayingAdapter(it.movies)
                        rvPlayingNow.adapter = nowMoviePlayingAdapter
                        nowMoviePlayingAdapter.setOnItemClicked { movie ->
                            showMovieDetails(movie.id)
                        }
                        startLoadingPopularMovies()
                    }
                }

                Status.ERROR -> {
                    rvPlayingNow.visibility = View.GONE
                    playingNowLoader.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Something went wrong. Please retry.",
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.loadMorePopularMovies()
                }
            }
        })
    }

    private fun startLoadingPopularMovies() {
        initScrollListeners()
        viewModel.loadMorePopularMovies()
        /*if (Utils.isNetworkAvailable(context)) {
        } else {
            showErrorAndEnd(R.string.error_no_internet)
        }*/
    }

    private fun setPopularMovieObserver() {
        viewModel.popularMoviesResponse.observe(this, Observer { response ->
            when (response.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    response.data?.let {
                        loading = false
                        val scrollToPosition = popularMovieAdapter.itemCount - 1
                        popularMovieAdapter.addItems(it.movies)
                        try {
                            if (viewModel.pageNo == 1) {
                                (rvPopular.layoutManager as LinearLayoutManager).scrollToPosition(0)
                            } else {
                                rvPopular.scrollToPosition(scrollToPosition)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                Status.ERROR -> {
                    loading = false
                    popularMovieAdapter.removeLoader()
                    Toast.makeText(
                        context,
                        "Something went wrong. Please retry.",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
        })
    }

    private fun showMovieDetails(movieId: Long) {
        if (!Utils.isNetworkAvailable(context)) {
            showError(R.string.error_no_internet)
            return
        }

        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra(Constants.MOVIE_ID, movieId)
        startActivity(intent)
    }

    private fun showErrorAndEnd(msg: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(msg)
        builder.setPositiveButton(R.string.button_ok, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                finish()
            }
        })
        builder.create().show()
    }

    private fun showError(msg: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(msg)
        builder.setPositiveButton(R.string.button_ok, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
            }
        })
        builder.create().show()
    }

    override fun onBackPressed() {
        finishAfterTransition();
    }

    fun f1() {
        GlobalScope.launch {
            println("Coroutine : F1 before")
            delay(2*60*1000)
            println("Coroutine : F1 after")
        }
    }

    fun f2() {
        GlobalScope.launch {
            println("Coroutine : F2 before")
            delay(300)
            println("Coroutine : F2 after")
        }
    }

    fun f3() {
        GlobalScope.launch {
            println("Coroutine : F3 before")
            delay(60*1000)
            println("Coroutine : F3 after")
        }

    }

    fun f4() {
        GlobalScope.launch {
            println("Coroutine : F4 before")
            delay(20*1000)
            println("Coroutine : F4 after")
        }
    }

    fun f5() {
        GlobalScope.launch {
            println("Coroutine : F5 before")
            delay(45*1000)
            println("Coroutine : F5 after")
            
        }
    }


}

// f2, f4, f5, f3, f1
