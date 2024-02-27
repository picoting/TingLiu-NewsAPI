package com.example.newsapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale.Category

interface NewsAPIService {
    @GET("top-headlines?country=us&apiKey=6bf72a75bf714927ac861e69b564a44b")
    fun getTopNewsByCategory(@Query("category") category: String): Call<NewsResponse>
}