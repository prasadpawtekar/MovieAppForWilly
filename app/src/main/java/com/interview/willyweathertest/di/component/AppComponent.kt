package com.interview.willyweathertest.di.component

import android.app.Application
import com.interview.willyweathertest.di.module.ActivityModule
import com.interview.willyweathertest.di.module.AppModule
import com.interview.willyweathertest.di.module.ViewModelFactoryModule
import com.interview.willyweathertest.di.module.ViewModelModule
import com.interview.willyweathertest.main.WillyApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class,
        ActivityModule::class
    ]
)
interface AppComponent: AndroidInjector<WillyApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application) : Builder
        fun build() : AppComponent
    }
}