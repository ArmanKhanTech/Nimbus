package com.android.nimbus.api

import com.android.nimbus.BuildConfig
import com.android.nimbus.model.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NimbusAPI {
    @GET("/news")
    suspend fun getNews(
        @Query("key") key: String = BuildConfig.NEWS_API_KEY,
    ): NewsModel

//    @GET("/weather")
//    suspend fun getWeather(
//        @Query("key") key: String = BuildConfig.WEATHER_API_KEY,
//    ): WeatherModel
}