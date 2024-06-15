package com.android.nimbus.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.android.nimbus.Screen
import com.android.nimbus.model.Article
import com.android.nimbus.model.NewsModel
import com.android.nimbus.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Calendar
import java.util.Locale

object SharedViewModel : ViewModel() {
    private val dataRepository = DataRepository()

    private val _news = MutableStateFlow(NewsModel())
    val news: StateFlow<NewsModel> = _news

//    val weather = MutableStateFlow(WeatherModel())

    init {
        fetchLatestNews()
    }

    private fun fetchLatestNews() {
        viewModelScope.launch {
            try {
                _news.value = dataRepository.getNews()
//                weather.value = nimbusRepository.getWeather()
            } catch (_: Exception) {
                Log.d("SharedViewModel", "Failed to fetch news")
            }
        }
    }

    fun searchArticles(query: String): List<Article> {
        val filteredArticles = news.value.articles.filter {
            it.title?.contains(query, ignoreCase = true) ?: false
        }
        return filteredArticles.distinctBy { it.title }
    }

    fun getArticlesByCategory(category: String): ArrayList<Article> {
        val filteredArticle = news.value.articles.filter { it.category == category }
        return filteredArticle as ArrayList
    }

    fun getArticleByID(id: String): Article {
        return news.value.articles.first { it.id == id }
    }

    fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        val formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH)
        return formatter.format(date)
    }

    fun openFeeds(
        navController: NavController,
        articleID: String?,
        category: String
    ) {
        if (articleID != null) {
            navController.navigate(
                Screen.FEED.name + "/$articleID&$category"
            )
        } else {
            navController.navigate(
                Screen.FEED.name + "/$category"
            )
        }
    }

    fun appendBookmarks(article: List<Article>) {
        for (i in article) {
            _news.value.articles.add(i)
        }
    }
}