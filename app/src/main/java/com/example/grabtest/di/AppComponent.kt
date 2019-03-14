package com.paytmmoney.di.component

import android.app.Application
import com.example.grabtest.di.AppModule
import com.example.grabtest.di.ViewModelModule
import com.example.grabtest.ui.application.MainApplication
import com.example.grabtest.ui.newsHome.NewsHomeFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created by Abhishek V on 23/11/2017.
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ViewModelModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: MainApplication)
    fun inject(fragment: NewsHomeFragment)
}