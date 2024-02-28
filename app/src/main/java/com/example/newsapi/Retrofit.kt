package com.example.newsapi

import com.example.newsapi.NewsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "Retrofit"
object Retrofit {
    private const val BASE_URL = "https://newsapi.org/v2/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val newsApi: NewsAPI by lazy {
        retrofit.create(NewsAPI::class.java)
    }
}