package com.android.nimbus.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NewsModel(
    @SerializedName("articles") var articles: ArrayList<Article> = arrayListOf(),
)

@Keep
data class Article(
    @SerializedName("author") var author: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("source_url") var sourceUrl: String? = null,
    @SerializedName("time") var time: String? = null,
    @SerializedName("title") var title: String? = null
)