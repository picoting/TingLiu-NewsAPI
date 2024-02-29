package com.example.newsapi

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.databinding.ListItemNewsBinding

class NewsHolder(private val binding: ListItemNewsBinding, private val listener: NewsListAdapter.OnItemClickListener) : RecyclerView.ViewHolder(binding.root)
{
    private var currentNews: News? = null
    init {
        binding.readMore.setOnClickListener {
            Log.d("NewsListAdapter", "Read more clicked for news: ${currentNews?.title}")
            currentNews?.let { news ->
                listener.onItemClick(news)
            }
        }
    }

    fun bind(news: News) {
        currentNews = news
        binding.newsAuthor.text = news.author
        binding.newsTitle.text = news.title
        binding.newsDescription.text = news.description


    }
}

class NewsListAdapter(private var newsList: List<News>, private val listener: NewsListFragment) : RecyclerView.Adapter<NewsHolder>() {
    private lateinit var onItemClick: (News) -> Unit

    interface OnItemClickListener {
        fun onItemClick(news: News)
    }

    fun setOnItemClickListener(listener: (News) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNewsBinding.inflate(inflater, parent, false)
        return NewsHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val news = newsList[position]
        holder.bind(news)

        /*
        holder.itemView.setOnClickListener {
            listener.onItemClick(news)
        }

         */
    }

    override fun getItemCount() = newsList.size

    fun updateList(newNewsList: List<News>) {
        newsList = newNewsList // update news
        notifyDataSetChanged()
        Log.d(
            "News Adapter",
            "updated news list"
        )
    }
}
