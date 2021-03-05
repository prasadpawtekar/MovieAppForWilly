package com.interview.willyweathertest.main

import android.app.Application
import com.interview.willyweathertest.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class WillyApp: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("native-lib")
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}