package com.android.nimbus.api

import com.android.nimbus.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("/news")
    suspend fun getNews(
        @Query("key") key: String = BuildConfig.NEWS_API_KEY,
    ): String
}