package com.interview.willyweathertest.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.interview.willyweathertest.R
import com.interview.willyweathertest.common.Utils
import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.databinding.HolderPopularMovieBinding
import java.text.SimpleDateFormat

class PopularMovieHolder(
    private val binding: HolderPopularMovieBinding
) : RecyclerView.ViewHolder(binding.root) {


    public fun bind(movie: Movie) {
        binding.titleTv.text = movie.title
        binding.ratingRv.setProgress(movie.voteAverage*10)
        val url: String = "https://image.tmdb.org/t/p/w200${movie.coverPhoto}"
        Glide.with(binding.root)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(binding.posterIv)
        binding.releaseDate.text = Utils.getDate(movie.releaseDate)
    }
}