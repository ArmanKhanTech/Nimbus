package com.android.nimbus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.android.nimbus.Screen
import com.android.nimbus.model.Article
import com.android.nimbus.model.NewsModel
import com.android.nimbus.model.WeatherModel
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

    private val _weather = MutableStateFlow(WeatherModel())
    val weather: StateFlow<WeatherModel> = _weather

    private val _city = MutableStateFlow<String?>("")
    val city: StateFlow<String?> = _city

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val newsModel = dataRepository.getNews()
                newsModel.articles = newsModel
                    .articles
                    .shuffled()
                    .distinctBy {
                        it.title
                    } as ArrayList<Article>
                _news.value = newsModel
            } catch (_: Exception) {
            }
        }
    }

    fun setCity(city: String?) {
        _city.value = city

        viewModelScope.launch {
            try {
                _weather.value = dataRepository.getWeather(_city.value.toString().lowercase())
            } catch (_: Exception) {
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
        return if (category != "recent") {
            val filteredArticle = news.value.articles.filter { it.category == category }
            ArrayList(filteredArticle)
        } else {
            val sortedArticles = news.value.articles.sortedBy { it.time }
            ArrayList(sortedArticles)
        }
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