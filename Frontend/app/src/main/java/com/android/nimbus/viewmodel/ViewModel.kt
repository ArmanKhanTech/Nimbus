package com.android.nimbus.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.nimbus.api.NewsAPI
import com.android.nimbus.model.NewsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewModel() : ViewModel() {
    private val newsApi: NewsAPI = Retrofit.Builder()
        .baseUrl("https://nimbus.armankhan.tech")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsAPI::class.java)

    var news = MutableStateFlow(mutableListOf(NewsModel()))

    init {
        viewModelScope.launch {
            try {
                val response = newsApi.getNews("tomcat", "all_news")
                news.value.addAll(response)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("ViewModel", "Error: ${e.message}")
            }
        }
    }

    fun fetchRecent(): List<NewsModel> {
        return news.value
    }

    fun fetchTopics(topic: String) {

    }

    fun filterTopics(topic: String) {

    }
}