package com.android.nimbus.model

import com.google.gson.annotations.SerializedName

data class NewsModel(
    @SerializedName("articles") var articles: ArrayList<Article> = arrayListOf(),
    @SerializedName("success") var success: Boolean? = null
)

data class Article(
    @SerializedName("author") var author: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("image_url") var image_url: String? = null,
    @SerializedName("source_url") var source_url: String? = null,
    @SerializedName("time") var time: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("url") var url: String? = null
)