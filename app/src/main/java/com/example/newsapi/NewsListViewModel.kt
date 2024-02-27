package com.example.newsapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapi.Retrofit.newsApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListViewModel : ViewModel() {

    private val _newsArticles = MutableLiveData<List<News>>()
    val newsArticles: LiveData<List<News>> = _newsArticles

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    init{
        viewModelScope.launch {
            newsApiService.getTopNewsByCategory("general").enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        _newsArticles.postValue(response.body()?.articles)
                        _isError.value = false
                    } else {
                        _isError.value = true
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    _isError.value = true
                }
            })
        }
    }

    fun fetchNewsByCategory(category: String) {
        viewModelScope.launch {
            newsApiService.getTopNewsByCategory(category).enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        _newsArticles.postValue(response.body()?.articles)
                        _isError.value = false
                    } else {
                        _isError.value = true
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    _isError.value = true
                }
            })
        }
    }
}