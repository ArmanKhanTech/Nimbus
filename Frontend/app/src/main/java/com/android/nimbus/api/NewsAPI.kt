package com.android.nimbus.api

import com.android.nimbus.model.NewsModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsAPI {
    @GET("/v2/latest_headlines")
    suspend fun getNews(
        @Header("x-api-key") apiKey: String,
        @Query("lang") lang: String = "en",
        @Query("countries") country: String = "IN",
        @Query("topic") topic: String = "news",
        @Query("when") from: String = "12h",
        @Query("page") page: Int = 100,
        @Query("page_size") pageSize: Int = 100
    ): MutableList<NewsModel>
}
