package com.interview.willyweathertest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.interview.willyweathertest.R
import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.data.model.NowPlayingMovie
import com.interview.willyweathertest.databinding.HolderPlayingNowBinding
import com.interview.willyweathertest.ui.holder.NowPlayingHolder

class NowPlayingAdapter (private val movies: List<NowPlayingMovie>):
    RecyclerView.Adapter<NowPlayingHolder>() {

    private lateinit var onItemClicked: (movie:NowPlayingMovie)->Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: HolderPlayingNowBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.holder_playing_now,
            parent,
            false
        )
        return NowPlayingHolder(binding)
    }

    override fun onBindViewHolder(playingHolder: NowPlayingHolder, position: Int) {
        playingHolder.bind(movies[position])
        onItemClicked?.let{
             playingHolder.itemView.setOnClickListener{
                onItemClicked(movies[position])
            }
        }
    }

    public fun setOnItemClicked(onItemClicked: (movie:NowPlayingMovie)->Unit) {
        this.onItemClicked = onItemClicked
    }
    override fun getItemCount(): Int = movies.size

}