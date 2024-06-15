package com.android.nimbus.ui.screens.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.nimbus.model.Article
import com.android.nimbus.repository.BookmarksRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async

class BookmarksViewModel : ViewModel() {
    private val bookmarksRepository = BookmarksRepository(
        FirebaseFirestore.getInstance()
    )

    suspend fun getBookmarks(): List<Article> {
        return viewModelScope.async {
            bookmarksRepository.getBookmarks()
        }.await()
    }

    suspend fun addBookmark(article: Article): Boolean {
        return viewModelScope.async {
            bookmarksRepository.addBookmark(article)
        }.await()
    }
}