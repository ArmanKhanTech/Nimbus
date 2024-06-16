package com.android.nimbus.repository

import androidx.annotation.Keep
import com.android.nimbus.model.Article
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.tasks.await

@Keep
data class Bookmark(
    @SerializedName("email") val email: String = "",
    @SerializedName("article") val article: Article = Article()
)

class BookmarksRepository(
    private val firestore: FirebaseFirestore
) {
    suspend fun getBookmarks(): List<Article> {
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email

        val result = mutableListOf<Article>()

        firestore.collection("bookmarks")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val article = document.toObject(Bookmark::class.java).article
                    if (result.any { it.title == article.title }) {
                        continue
                    } else {
                        result.add(article)
                    }
                }
            }
            .await()

        return result
    }

    suspend fun addBookmark(article: Article): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email

        var status = false

        val bookmarks = getBookmarks()
        if (bookmarks.contains(article)) {
            return false
        } else {
            article.category = "bookmarks"

            val bookmark = Bookmark(email.toString(), article)
            firestore.collection("bookmarks")
                .add(bookmark)
                .addOnSuccessListener {
                    status = true
                }
                .addOnFailureListener { e ->
                    status = false
                }
                .await()

            return status
        }
    }
}