package com.example.newsapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapi.Retrofit.newsApi
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
            newsApi.getTopNewsByCategory("general").enqueue(object : Callback<NewsResponse> {
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
        Log.d("NewsListViewModel", "Fetching from category $category")
        viewModelScope.launch {
            newsApi.getTopNewsByCategory(category).enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        Log.d("NewsListViewModel", "Fetching from category $category successful")
                        _newsArticles.postValue(response.body()?.articles)
                        _isError.postValue(false)
                    } else {
                        Log.d("NewsListViewModel", "Fetching from category $category unsuccessful: ${response.message()}")
                        _isError.postValue(true)
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.d("NewsListViewModel", "Exception while fetching from category $category: ${t.message}")
                    _isError.postValue(true)
                }
            })
        }
    }
}