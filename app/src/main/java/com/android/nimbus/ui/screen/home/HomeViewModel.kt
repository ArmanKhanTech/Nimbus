package com.android.nimbus.ui.screen.home

import androidx.lifecycle.ViewModel
import com.android.nimbus.api.NewsAPI
import com.android.nimbus.model.TopStoriesModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel(private val apiKey: String) : ViewModel() {
    private val newsApi: NewsAPI = Retrofit.Builder()
        .baseUrl("https://api.nytimes.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsAPI::class.java)

    suspend fun fetchTopStories(): TopStoriesModel {
        return newsApi.getTopStories(apiKey)
    }
}