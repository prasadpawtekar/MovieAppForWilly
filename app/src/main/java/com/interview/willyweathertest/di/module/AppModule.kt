package com.interview.willyweathertest.di.module

import android.app.Application
import com.google.gson.Gson
import com.interview.willyweathertest.BuildConfig
import com.interview.willyweathertest.data.local.MovieDatabase
import com.interview.willyweathertest.data.remote.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
@Module
class AppModule {
    external fun apiBaseUrl(): String
    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder().also {
        it.client(okHttpClient)
        it.addConverterFactory(GsonConverterFactory.create())
        it.baseUrl(apiBaseUrl())

    }.build()

    @Singleton
    @Provides
    fun getOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder().also {
        if(BuildConfig.DEBUG) {
            it.addInterceptor(loggingInterceptor)
        }
    }.build()

    @Singleton
    @Provides
    fun providesLogInterceptor() = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java);

    @Singleton
    @Provides
    fun providesDb(application: Application) = MovieDatabase.getInstance(application)

    @Singleton
    @Provides
    fun providesNowPlayingDao(movieDatabase: MovieDatabase) = movieDatabase.nowPlayingMovieDao

    @Singleton
    @Provides
    fun providesMovieDao(movieDatabase: MovieDatabase) = movieDatabase.movieDao

    @Singleton
    @Provides
    fun providesAppContext(application: Application) = application.applicationContext

    @Singleton
    @Provides
    fun providesGson() = Gson()
}