package com.android.nimbus.api

import com.android.nimbus.BuildConfig
import com.android.nimbus.model.NewsModel
import com.android.nimbus.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NimbusAPI {
    @GET("/news")
    suspend fun getNews(
        @Query("key") key: String = BuildConfig.API_KEY,
    ): NewsModel

    @GET("/weather")
    suspend fun getWeather(
        @Header("Cache-Control") cacheControl: String = "no-cache",
        @Query("key") key: String = BuildConfig.API_KEY,
        @Query("city") city: String,
    ): WeatherModel
}