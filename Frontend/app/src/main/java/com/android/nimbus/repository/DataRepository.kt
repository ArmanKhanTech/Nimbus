package com.android.nimbus.repository

import com.android.nimbus.api.NimbusAPI
import com.android.nimbus.model.NewsModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataRepository {
    private val nimbusAPI: NimbusAPI = Retrofit.Builder()
        .baseUrl("https://nimbus.armankhan.tech")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NimbusAPI::class.java)

    suspend fun getNews(): NewsModel {
        return nimbusAPI.getNews()
    }

//    suspend fun getWeather(): WeatherModel {
//        return nimbusAPI.getWeather()
//    }
}