package com.android.nimbus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.nimbus.api.NewsAPI
import com.android.nimbus.model.Article
import com.android.nimbus.model.NewsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.Calendar
import java.util.Locale

class ViewModel() : ViewModel() {
    private val newsAPI: NewsAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://nimbus.armankhan.tech")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPI::class.java)
    }

    private var news = MutableStateFlow(NewsModel())
    var isLoading = MutableStateFlow(true)

    var feedsTitle = "All News"
    var currentArticleInFeed: String? = null

    init {
        viewModelScope.launch {
            val response = newsAPI.getNews()
            news.value = response
        }.invokeOnCompletion {
            isLoading.value = false
        }
    }

    fun getArticleByID(id: String): NewsModel {
        val filteredNews = ArrayList<Article>()
        news.value.articles.filterTo(filteredNews) { it.id == id }
        return NewsModel(filteredNews, true)
    }

    fun getArticlesByCategory(category: String): NewsModel {
        val filteredNews = ArrayList<Article>()
        news.value.articles.filterTo(filteredNews) { it.category == category }
        return NewsModel(filteredNews, true)
    }

    fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        val formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH)
        return formatter.format(date)
    }
}