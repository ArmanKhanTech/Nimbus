package com.android.nimbus.model

import com.google.gson.annotations.SerializedName

data class NewsModel(
    @SerializedName("category") var category: String? = null,
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf(),
    @SerializedName("success") var success: Boolean? = null
)

data class Data(
    @SerializedName("author") var author: String? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("imageUrl") var imageUrl: String? = null,
    @SerializedName("readMoreUrl") var readMoreUrl: String? = null,
    @SerializedName("time") var time: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("url") var url: String? = null
)