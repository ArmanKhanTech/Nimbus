package com.android.nimbus.api

import com.android.nimbus.model.NewsModel
import com.android.nimbus.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NimbusAPI {
    @GET("/news")
    suspend fun getNews(
        @Query("key") key: String = "tomistheking",
    ): NewsModel

    @GET("/weather")
    suspend fun getWeather(
        @Query("key") key: String = "tomistheking",
        @Query("city") city: String,
    ): WeatherModel
}