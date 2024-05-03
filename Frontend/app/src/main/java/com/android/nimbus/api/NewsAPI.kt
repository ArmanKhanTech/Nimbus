package com.android.nimbus.api

import com.android.nimbus.model.NewsModel
import retrofit2.http.GET

interface NewsAPI {
    @GET("/news")
    suspend fun getNews(): NewsModel
}