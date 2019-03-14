package com.example.grabtest.ui.application

import android.app.Application
import android.content.Context
import com.paytmmoney.di.component.DaggerAppComponent
import com.paytmmoney.di.component.AppComponent


class MainApplication : Application() {


    companion object {
        var appComponent: AppComponent? = null
        var context: Context? = null
    }


    override fun onCreate() {
        super.onCreate()
        context = baseContext
        initDagger();
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build();
        appComponent!!.inject(this);
    }
}
