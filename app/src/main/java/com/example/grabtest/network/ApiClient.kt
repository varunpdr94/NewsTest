package com.example.grabtest.network


import com.example.grabtest.ui.application.MainApplication
import okhttp3.Cache

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {


    companion object {
        var cacheSize: Long = java.lang.Long.valueOf((100 * 1024 * 1024).toLong())

        val BASE_URL = "https://newsapi.org/"
        private var retrofit: Retrofit? = null


        val client: Retrofit
            get() {
                if (retrofit == null) {

                    val myCache = Cache(MainApplication.context?.cacheDir, cacheSize)


                    val builder = OkHttpClient.Builder()
                    builder.cache(myCache)

                    builder.addNetworkInterceptor(NetworkInterceptor())

                    val client = builder.build()



                    retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(client)
                            .build()
                }
                return retrofit!!
            }
    }


}
