package com.interview.willyweathertest.di.module

import com.interview.willyweathertest.ui.main.MainActivity
import com.interview.willyweathertest.ui.moviedetails.MovieDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesMovieDetailsActivity(): MovieDetailsActivity

}