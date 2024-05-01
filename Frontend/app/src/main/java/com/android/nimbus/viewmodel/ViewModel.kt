package com.android.nimbus.viewmodel

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.nimbus.api.NewsAPI
import com.android.nimbus.data.topicsTitles
import com.android.nimbus.model.NewsModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewModel(val context: Context) : ViewModel() {
    private val apiKey: String = getAPIKey() ?: ""

    private val newsApi: NewsAPI = Retrofit.Builder()
        .baseUrl("https://api.newscatcherapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsAPI::class.java)

    private var news = MutableStateFlow(mutableListOf(NewsModel()))

    init {
        viewModelScope.launch {
            for(topic in topicsTitles) {
                val newsData = newsApi.getNews(apiKey, topic = topic)
                news.value.addAll(newsData)
                delay(1000)
            }
        }
    }

    fun fetchRecent(): List<NewsModel> {
        return news.value.filter {
            it.userInput?.topic == "news"
        }
    }

    fun fetchTopics(topic: String) {

    }

    fun filterTopics(topic: String) {

    }

    private fun getAPIKey(): String? {
        val application = context.applicationContext as Application
        val applicationInfo: ApplicationInfo = application.packageManager
            .getApplicationInfo(application.packageName, PackageManager.GET_META_DATA)
        return applicationInfo.metaData.getString("NEWS_API_KEY")
    }
}