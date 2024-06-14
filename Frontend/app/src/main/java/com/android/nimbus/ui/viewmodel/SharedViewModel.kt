package com.android.nimbus.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.nimbus.model.Article
import com.android.nimbus.model.NewsModel
import com.android.nimbus.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Calendar
import java.util.Locale

object SharedViewModel : ViewModel() {
    private val dataRepository: DataRepository = DataRepository()

    val news = MutableStateFlow(NewsModel())
//    val weather = MutableStateFlow(WeatherModel())

    init {
        fetchLatestNews()
    }

    private fun fetchLatestNews() {
        viewModelScope.launch {
            try {
                news.value = dataRepository.getNews()
//                weather.value = nimbusRepository.getWeather()
            } catch (_: Exception) {
                Log.d("SharedViewModel", "Failed to fetch news")
            }
        }
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
}