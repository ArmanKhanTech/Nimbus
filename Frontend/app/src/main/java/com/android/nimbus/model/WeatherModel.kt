package com.android.nimbus.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherModel(
    @SerializedName("daily") var daily: ArrayList<Daily> = arrayListOf()
)

@Keep
data class Daily(
    @SerializedName("date") var date: String? = null,
    @SerializedName("hourly") var hourly: ArrayList<Hourly> = arrayListOf(),
    @SerializedName("temperature") var temperature: Int? = null
)

@Keep
data class Hourly(
    @SerializedName("description") var description: String? = null,
    @SerializedName("kind") var kind: String? = null,
    @SerializedName("temperature") var temperature: Int? = null,
    @SerializedName("time") var time: String? = null
)