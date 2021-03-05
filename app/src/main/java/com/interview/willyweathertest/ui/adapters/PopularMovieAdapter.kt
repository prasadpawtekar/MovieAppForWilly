package com.interview.willyweathertest.ui.adapters

import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.interview.willyweathertest.R
import com.interview.willyweathertest.data.model.LoaderMsg
import com.interview.willyweathertest.data.model.Movie
import com.interview.willyweathertest.databinding.HolderLoaderBinding
import com.interview.willyweathertest.databinding.HolderPopularMovieBinding
import com.interview.willyweathertest.ui.holder.LoaderHolder
import com.interview.willyweathertest.ui.holder.PopularMovieHolder

class PopularMovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = ArrayList<Any>()
    private lateinit var onItemClicked: (movie: Movie)->Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ViewType.VIEW_TYPE_LOADER -> {
                val binding: HolderLoaderBinding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.holder_loader,
                    parent,
                    false
                )
                return LoaderHolder(binding)
            }

            ViewType.VIEW_TYPE_MOVIE -> {
                val binding: HolderPopularMovieBinding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.holder_popular_movie,
                    parent,
                    false
                )
                return PopularMovieHolder(binding)
            }

            else -> return super.createViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PopularMovieHolder -> {
                holder.bind(items[position] as Movie)

                onItemClicked?.let {

                    onItemClicked?.let{

                        holder.itemView.setOnClickListener{
                            onItemClicked(items[position] as Movie)
                        }

                    }
                }
            }

            is LoaderHolder -> {
                holder.bind(items[position] as LoaderMsg)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int) =
        when (items[position]) {
            is Movie -> ViewType.VIEW_TYPE_MOVIE
            is LoaderMsg -> ViewType.VIEW_TYPE_LOADER
            else -> super.getItemViewType(position)
        }

    object ViewType {
        val VIEW_TYPE_MOVIE = 1
        val VIEW_TYPE_LOADER = 2
    }

    public fun addItems(list: List<Any>) {
        if(items.size>0 && items[itemCount-1] is LoaderMsg) {
            val removed = items.removeAt(items.size-1)
            Log.e("okhttp", "Loader removed $removed")
        }
        items.addAll(list)
        notifyDataSetChanged()
    }

    public fun addLoader(msg: String) {
        items.add(LoaderMsg(msg))
        notifyItemChanged(items.size-1)
    }

    public fun removeLoader() {
        if(items.size>0 && items[itemCount-1] is LoaderMsg) {
            val removed = items.removeAt(items.size-1)

            notifyDataSetChanged()
            Log.e("okhttp", "Loader removed $removed")
        }
    }

    public fun setOnItemClicked(onItemClicked: (movie:Movie)->Unit) {
        this.onItemClicked = onItemClicked
    }
}