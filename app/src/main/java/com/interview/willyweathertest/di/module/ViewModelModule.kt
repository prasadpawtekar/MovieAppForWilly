package com.interview.willyweathertest.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.interview.willyweathertest.di.factory.ViewModelFactory
import com.interview.willyweathertest.di.factory.ViewModelKey
import com.interview.willyweathertest.ui.main.MainViewModel
import com.interview.willyweathertest.ui.moviedetails.MovieDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    protected abstract fun movieDetailsViewModel(movieDetailsViewModel: MovieDetailsViewModel): ViewModel
}