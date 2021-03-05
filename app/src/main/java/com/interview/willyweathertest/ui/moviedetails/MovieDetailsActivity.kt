package com.interview.willyweathertest.ui.moviedetails

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.interview.willyweathertest.R
import com.interview.willyweathertest.common.Constants
import com.interview.willyweathertest.common.Resource
import com.interview.willyweathertest.common.Status
import com.interview.willyweathertest.common.Utils
import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.databinding.ActivityMovieDetailsBinding
import com.interview.willyweathertest.di.factory.ViewModelFactory
import com.interview.willyweathertest.ui.custom.TagView
import dagger.android.AndroidInjection
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: MovieDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var context: Context
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        context = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        try {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        setObservers()
        viewModel.movieId.value = intent.getLongExtra(Constants.MOVIE_ID, 0)

    }

    private fun setObservers() {
        setMovieIdObserver()
        setMovieDetailsObserver()
    }

    private fun setMovieIdObserver() {
        viewModel.movieId.observe(this, { movieId ->
            if(Utils.isNetworkAvailable(context)) {
                viewModel.loadMovieDetails()
            } else {
                showErrorAndEnd(R.string.error_no_internet)
            }
        })
    }


    private fun setMovieDetailsObserver() {

        viewModel.movieDetails.observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    binding.groupAllContents.visibility = View.GONE
                    binding.loaderPb.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    resource.data?.let {
                        binding.groupAllContents.visibility = View.VISIBLE
                        binding.loaderPb.visibility = View.GONE
                        binding.data = it
                        //binding.releaseDate.text = Utils.getReleaseAndDuration(it)
                        val url = "https://image.tmdb.org/t/p/w400${it.coverPhoto}"
                        Glide.with(binding.root)
                            .load(url)
                            .centerCrop()
                            .placeholder(R.drawable.placeholder)
                            .into(binding.posterIv)
                        loadGenres(it)
                    }
                }

                Status.ERROR -> {
                    binding.loaderPb.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Something went wrong. Please retry.",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadGenres(movie: Movie) {
        movie.genres?.let {
            val tags: Array<TagView.Tag?> = Array(movie.genres.size, {null})

            for (i in 0 until movie.genres.size) {
                tags[i] = TagView.Tag(movie.genres[i].name, Color.parseColor("#ffffff"))
            }
            binding.tagView.setTags(tags, "  ")
        }
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
}