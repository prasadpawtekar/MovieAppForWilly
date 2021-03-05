package com.interview.willyweathertest.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.interview.willyweathertest.R
import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.data.model.NowPlayingMovie
import com.interview.willyweathertest.databinding.HolderPlayingNowBinding

class NowPlayingHolder (
        private val binding: HolderPlayingNowBinding
    ) : RecyclerView.ViewHolder(binding.root){

    public fun bind(movie: NowPlayingMovie) {
        val path:String = "https://image.tmdb.org/t/p/w500${movie.coverPhoto}"
        Glide.with(binding.root)
            .load(path)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(binding.ivPoster)
    }
}