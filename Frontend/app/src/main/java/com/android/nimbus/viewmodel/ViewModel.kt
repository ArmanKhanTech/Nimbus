package com.android.nimbus.viewmodel

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

    private var news = MutableStateFlow(NewsModel())
    var isLoading = MutableStateFlow(true)

    var feedsTitle = "All News"
    var currentArticleInFeed: String? = null

    init {
        viewModelScope.launch {
            try {
                val response = newsApi.getNews()
                news.value = response
                isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getArticleByID(id: String): NewsModel {
        val filteredNews = news.value.articles.filter { it.id == id } as ArrayList
        return NewsModel(filteredNews, true)
    }

    fun getArticlesByCategory(category: String): NewsModel {
        val filteredNews = news.value.articles.filter { it.category == category } as ArrayList
        return NewsModel(filteredNews, true)
    }
}