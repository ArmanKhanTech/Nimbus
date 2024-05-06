package com.android.nimbus.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.nimbus.api.NewsAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class SplashViewModel : ViewModel() {
    private val newsAPI: NewsAPI = Retrofit.Builder()
        .baseUrl("https://nimbus.armankhan.tech")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(NewsAPI::class.java)

    var isLoading = MutableStateFlow(true)
    var response = MutableStateFlow("")

    init {
        viewModelScope.launch {
            if(response.value.isEmpty()) {
                response.value = newsAPI.getNews()
                isLoading.value = false
            }
        }
    }
}