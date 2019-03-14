package com.example.grabtest.network;

import android.util.Log
import com.example.grabtest.ui.application.MainApplication
import com.example.grabtest.utils.NetworkUtils
import okhttp3.*

import java.io.IOException

import okio.Buffer


/**
 * Created by Madan Mohan Yadav on 11/09/2017 AD
 */

class NetworkInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {


        var newRequest = chain.request()

        val rb = newRequest.body()

        val buffer = Buffer()
        rb?.writeTo(buffer)

        val builder = chain.request().newBuilder()
                .addHeader("X-Api-Key", NetworkUtils.apiKey)


        newRequest = if (NetworkUtils.hasNetwork(MainApplication.context!!)!!) {
            builder.addHeader("Cache-Control", "public, max-age=" + 5).cacheControl(CacheControl.FORCE_CACHE).build()
        } else{
            builder.addHeader("Cache-Control","public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
        }

        Log.d(javaClass.simpleName, "request payload" + " : " + newRequest.method() + ", " + newRequest.url())
        Log.d(javaClass.simpleName, "request headers" + buffer.readUtf8() + "\n Headers:::" + newRequest.headers().toString())
        val response = chain.proceed(newRequest)
        val responseString = String(response.body()!!.bytes())
        Log.d(javaClass.simpleName, "response payload" + response.code() + "\n" + responseString)
        Log.d(javaClass.simpleName, "response headers" + buffer.readUtf8() + "\n Headers:::" + response.headers().toString())



        return response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", String.format("max-age=%d", 60))
                .body(ResponseBody.create(response.body()!!.contentType(), responseString))
                .build()
    }
}
