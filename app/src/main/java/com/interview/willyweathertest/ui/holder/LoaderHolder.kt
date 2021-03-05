package com.interview.willyweathertest.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.interview.willyweathertest.data.model.LoaderMsg
import com.interview.willyweathertest.databinding.HolderLoaderBinding

class LoaderHolder(private val binding: HolderLoaderBinding): RecyclerView.ViewHolder(binding.root) {
    public fun bind(loaderMsg: LoaderMsg) {
        binding.textView.text = loaderMsg.msg
    }
}