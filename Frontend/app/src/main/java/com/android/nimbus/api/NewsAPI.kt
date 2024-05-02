package com.android.nimbus.api

import com.android.nimbus.model.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("/news")
    suspend fun getNews(
        @Query("key") key: String,
        @Query("category") category: String,
    ): MutableList<NewsModel>
}
