package com.example.newsapi

data class NewsResponse(
    val articles: List<News>,
    val status: String,
)

data class News(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val publishedAt: String,
    val urlToImage: String,
    val content: String?
)

data class Source(
    val id: String?,
    val name: String
)