package com.example.newsapi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.databinding.FragmentNewsListBinding


private const val TAG = "NewsListFragment"

class NewsListFragment: Fragment() {
    private var _binding: FragmentNewsListBinding? = null
    private lateinit var newsAdapter: NewsListAdapter
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val newsListViewModel: NewsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total articles: ${newsListViewModel.newsArticles}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)

        binding.recyclerViewNews.layoutManager = LinearLayoutManager(context)

        //i need to set up the adapter TT
        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsListViewModel.newsArticles.observe(viewLifecycleOwner) { newsArticles ->
            // Update the adapter's data when the newsArticles LiveData changes
            newsAdapter.updateList(newsArticles)
        }

        setupSpinner()
    }

    fun setupRecyclerView() {
        val newsAdapter = NewsListAdapter(emptyList()) // Initialize your adapter with empty or initial data
        binding.recyclerViewNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

    }

    private fun setupSpinner() {
        val categorySpinner = binding.spinnerNewsCategories

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // get category from the spinner
                val selectedCategory = parent?.getItemAtPosition(position).toString()
                //  fetch news from category using viewmodel?
                newsListViewModel.fetchNewsByCategory(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                newsListViewModel.fetchNewsByCategory("General")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
