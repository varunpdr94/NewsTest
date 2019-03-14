package com.example.grabtest.di

import com.example.grabtest.network.ApiClient
import com.example.grabtest.network.RestServiceInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Abhishek V on 23/11/2017.
 */
@Module
class AppModule {


    @Provides
    @Singleton
    fun provideRestService(): RestServiceInterface {
        val retrofit = ApiClient.client
        return retrofit.create(RestServiceInterface::class.java)
    }

}