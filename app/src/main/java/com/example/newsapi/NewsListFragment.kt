package com.example.newsapi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.databinding.FragmentNewsListBinding


private const val TAG = "NewsListFragment"
class NewsListFragment: Fragment() {
    private var _binding: FragmentNewsListBinding? = null
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

        //idk where newsRecyclerView is from
        //binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        val news = newsListViewModel.newsArticles

        //i need to set up the adapter TT
        //val adapter = NewsListAdapter(crimes)
        //binding.newsRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        // viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        // Setup RecyclerView
        setupRecyclerView()

        // Setup Spinner
        setupSpinner()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(listOf()) // Initialize your adapter with empty or initial data
        binding.recyclerViewNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        // Observing data from ViewModel and updating RecyclerView
        // viewModel.newsArticles.observe(viewLifecycleOwner) { articles ->
        //     newsAdapter.updateData(articles)
        // }
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.news_categories, // Ensure this array is defined in your strings.xml
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerNewsCategories.adapter = adapter
        }

        binding.spinnerNewsCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val category = parent.getItemAtPosition(position).toString()
                // Fetch news based on the selected category
                // viewModel.fetchNewsByCategory(category)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
