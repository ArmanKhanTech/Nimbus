package com.android.nimbus.ui.screen.feeds

import androidx.lifecycle.ViewModel
import com.android.nimbus.model.Article
import com.android.nimbus.model.NewsModel

class FeedsViewModel(
    private val newsModel: NewsModel,
    val category: String,
    val articleID: String?,
) : ViewModel() {
    fun getArticlesByCategory(category: String): NewsModel {
        val filteredNews = ArrayList<Article>()
        newsModel.articles.filterTo(filteredNews) { it.category == category }
        return NewsModel(filteredNews, true)
    }

    fun getArticleByID(id: String): NewsModel {
        val filteredNews = ArrayList<Article>()
        newsModel.articles.filterTo(filteredNews) { it.id == id }
        return NewsModel(filteredNews, true)
    }
}