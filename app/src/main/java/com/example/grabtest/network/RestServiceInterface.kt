package com.example.grabtest.network

import com.example.grabtest.ui.newsHome.model.NewsListResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface RestServiceInterface {


    @GET
    fun getNewsData(@Url url: String, @QueryMap queryMap: @JvmSuppressWildcards Map<String,Any>): Observable<Response<NewsListResponse>>

}
