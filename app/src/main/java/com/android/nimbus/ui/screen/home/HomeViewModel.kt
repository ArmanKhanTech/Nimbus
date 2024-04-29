package com.android.nimbus.ui.screen.home

import androidx.lifecycle.ViewModel
import com.android.nimbus.api.NewsAPI
import com.android.nimbus.model.NewsModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel(private val apiKey: String) : ViewModel() {
    private val newsApi: NewsAPI = Retrofit.Builder()
        .baseUrl("https://api.newscatcherapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsAPI::class.java)

    suspend fun fetchTopStories(): NewsModel {
        return newsApi.getTopStories(apiKey)
    }

    suspend fun fetchRecent(): NewsModel {
        return newsApi.getRecent(apiKey)
    }

    suspend fun fetchTopics(topic: String): NewsModel {
        return newsApi.getTopics(apiKey, topic = topic)
    }
}