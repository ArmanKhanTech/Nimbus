package com.android.nimbus.api

import com.android.nimbus.model.NewsModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsAPI {
    @GET("/v2/latest_headlines")
    suspend fun getTopStories(
        @Header("x-api-key") apiKey: String,
        @Query("lang") lang: String = "en",
        @Query("countries") country: String = "IN",
        @Query("when") from: String = "12h",
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 4
    ): NewsModel

    @GET("/v2/latest_headlines")
    suspend fun getRecent(
        @Header("x-api-key") apiKey: String,
        @Query("lang") lang: String = "en",
        @Query("countries") country: String = "IN",
        @Query("when") from: String = "1h",
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 4
    ): NewsModel

    @GET("/v2/latest_headlines")
    suspend fun getTopics(
        @Header("x-api-key") apiKey: String,
        @Query("lang") lang: String = "en",
        @Query("countries") country: String = "IN",
        @Query("when") from: String = "24h",
        @Query("topic") topic: String,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 4
    ): NewsModel
}
