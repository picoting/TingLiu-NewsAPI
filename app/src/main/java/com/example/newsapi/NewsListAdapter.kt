package com.example.newsapi

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.databinding.ListItemNewsBinding

class NewsHolder(private val binding: ListItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(news: News) {
        binding.newsTitle.text = news.title
        binding.newsDescription.text = news.description

        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context, "${news.title} clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}

class NewsListAdapter(private val newsList: List<News>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val REGULAR_NEWS = 1
        private const val FEATURED_NEWS = 0
    }

    override fun getItemViewType(position: Int): Int {
        val news = newsList[position]
        return if (news.isFeatured) FEATURED_NEWS else REGULAR_NEWS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            FEATURED_NEWS -> {
                val featuredBinding = FeaturedListItemNewsBinding.inflate(inflater, parent, false)
                FeaturedNewsHolder(featuredBinding)
            }
            else -> {
                val binding = ListItemNewsBinding.inflate(inflater, parent, false)
                NewsHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val news = newsList[position]
        when (holder) {
            is NewsHolder -> holder.bind(news)
            is FeaturedNewsHolder -> holder.bind(news)
            else -> throw IllegalArgumentException("Invalid view holder type")
        }
    }

    override fun getItemCount() = newsList.size
}