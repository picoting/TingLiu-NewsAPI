package com.example.newsapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapi.databinding.FragmentNewsDetailBinding

class NewsDetailFragment : Fragment() {
    private var _binding: FragmentNewsDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    // Use Safe Args to retrieve passed data
    //private val args: NewsDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Populate your views with the News object passed from NewsListFragment
        binding.apply {
            val title = arguments?.getString("title")
            val author = arguments?.getString("author")
            val content = arguments?.getString("content")

            view.findViewById<TextView>(R.id.title_view).text = title
            view.findViewById<TextView>(R.id.author_view).text = content
            view.findViewById<TextView>(R.id.content_view).text = author

            /*
            args.news.urlToImage?.let { imageUrl ->
                Glide.with(this@NewsDetailFragment)
                    .load(imageUrl)
                    .into(newsImageView)
            }
            */

            // Back button click listener to go back to the previous fragment
            backToHome.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
