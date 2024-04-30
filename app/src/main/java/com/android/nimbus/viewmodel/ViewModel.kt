package com.android.nimbus.ui.screen.home

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.nimbus.api.NewsAPI
import com.android.nimbus.model.NewsModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel(val context: Context) : ViewModel() {
    private val apiKey: String = getAPIKey() ?: ""

    private val newsApi: NewsAPI = Retrofit.Builder()
        .baseUrl("https://api.newscatcherapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsAPI::class.java)

    var topStories = MutableStateFlow(NewsModel())
    var recent = MutableStateFlow(NewsModel())

    private var visitedTopics = mutableListOf("Beauty")
    var topics = MutableStateFlow(mutableListOf<NewsModel>())
    var selectedTopic = MutableStateFlow(NewsModel())

    fun fetchTopStories() {
        viewModelScope.launch {
            delay(3000)
            topStories.value = newsApi.getTopStories(apiKey)
        }
    }

    fun fetchRecent() {
        viewModelScope.launch {
            delay(3000)
            recent.value = newsApi.getRecent(apiKey)
        }
    }

    fun fetchTopics(topic: String) {
        viewModelScope.launch {
            delay(3000)
            if(!visitedTopics.contains(topic)) {
                visitedTopics.add(topic)
                val newsModel = newsApi.getTopics(apiKey, topic = topic)
                topics.value.add(newsModel)
            }
        }
    }

    fun getFilteredTopics(topic: String) {
        selectedTopic.value = topics.value.find { it.userInput?.topic == topic } ?: NewsModel()
    }

    private fun getAPIKey(): String? {
        val application = context.applicationContext as Application
        val applicationInfo: ApplicationInfo = application.packageManager
            .getApplicationInfo(application.packageName, PackageManager.GET_META_DATA)
        return applicationInfo.metaData.getString("NEWS_API_KEY")
    }
}