package com.example.newsapi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.databinding.FragmentNewsListBinding


private const val TAG = "NewsListFragment"

class NewsListFragment: Fragment(), NewsListAdapter.OnItemClickListener {
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
        //setupRecyclerView()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        newsListViewModel.newsArticles.observe(viewLifecycleOwner) { newsArticles ->
            // Update the adapter's data when the newsArticles LiveData changes
            if (newsArticles != null) {
                Log.d(TAG, "Observer received new news articles, updating adapter.")
                newsAdapter.updateList(newsArticles)
            } else {
                Log.d(TAG, "Observer received null for new news articles.")
            }
        }

        setupSpinner()


    }

    private fun setupRecyclerView() {

        newsAdapter = NewsListAdapter(emptyList(), this) // initialize your adapter with empty or initial data
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
                Log.d(
                    "News List Fragment",
                    "Category changed to: $selectedCategory"
                )
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

    override fun onItemClick(news: News) {

        val bundle = Bundle().apply {
            putString("title", news.title)
            putString("description", news.description)
            putString("content", news.content)
            putString("urlToImage", news.urlToImage)
        }
        Log.d(TAG, "Attempting to navigate to NewsDetailFragment")

        if (isAdded) { // Check if fragment is currently added to its activity
            Log.d(TAG, "fragment added to activity")
            try {
                // Create an instance of the destination fragment
                val newsDetailFragment = NewsDetailFragment().apply {
                    arguments = bundle // Pass the bundle to the fragment
                }

                // Perform the fragment transaction using parentFragmentManager
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment, newsDetailFragment) // Use the correct container ID
                    addToBackStack(null) // Add this transaction to the back stack (optional)
                    commit() // Commit the transaction
                }

                Log.d(TAG, "Navigation to NewsDetailFragment initiated")
            } catch (e: Exception) {
                Log.e(TAG, "Error navigating to NewsDetailFragment", e)
            }
        }

    }


}
