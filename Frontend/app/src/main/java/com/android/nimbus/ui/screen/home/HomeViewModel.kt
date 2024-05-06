package com.android.nimbus.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.android.nimbus.Screen
import com.android.nimbus.model.Article
import com.android.nimbus.model.NewsModel
import com.google.gson.Gson
import java.text.DateFormat
import java.util.Calendar
import java.util.Locale

class HomeViewModel(
    response: String
) : ViewModel() {
    private var newsModel: NewsModel = NewsModel()

    init {
        val jsonString = Gson().fromJson(response, String::class.java)
        newsModel = Gson().fromJson(jsonString, NewsModel::class.java)
    }

    fun openFeeds(
        navController: NavController,
        category: String,
        articleID: String?
    ) {
        navController.currentBackStackEntry?.savedStateHandle?.set("newsModel", newsModel)
        navController.currentBackStackEntry?.savedStateHandle?.set("category", category)
        articleID?.let {
            navController.currentBackStackEntry?.savedStateHandle?.set("articleID", it)
        }
        navController.navigate(Screen.FEEDS.name)
    }

    fun getArticlesByCategory(category: String): NewsModel {
        val filteredNews = ArrayList<Article>()
        newsModel.articles.filterTo(filteredNews) { it.category == category }
        return NewsModel(filteredNews, true)
    }

    fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        val formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH)
        return formatter.format(date)
    }
}