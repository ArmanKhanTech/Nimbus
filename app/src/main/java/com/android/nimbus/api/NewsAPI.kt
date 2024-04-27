package com.android.nimbus.api

import com.android.nimbus.model.TopStoriesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("svc/topstories/v2/home.json")
    suspend fun getTopStories(@Query("api-key") apiKey: String): TopStoriesModel
}
