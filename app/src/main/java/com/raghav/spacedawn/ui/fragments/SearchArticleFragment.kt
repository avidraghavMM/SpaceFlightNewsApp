package com.raghav.spacedawn.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raghav.spacedawn.R
import com.raghav.spacedawn.adapters.SearchArticlesAdapter
import com.raghav.spacedawn.databinding.FragmentSearchArticleBinding
import com.raghav.spacedawn.ui.viewmodels.SearchArticleFragmentVM
import com.raghav.spacedawn.utils.Constants
import com.raghav.spacedawn.utils.Constants.Companion.DELAY_TIME
import com.raghav.spacedawn.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchArticleFragment : Fragment(R.layout.fragment_search_article) {

    private val viewModel by viewModels<SearchArticleFragmentVM>()
    private lateinit var searchArticlesAdapter: SearchArticlesAdapter
    private lateinit var binding: FragmentSearchArticleBinding

    companion object {
        private const val TAG = "SearchArticleFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchArticleBinding.bind(view)
        setupRecyclerView()

        searchArticlesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchArticleFragment_to_articleDisplayFragment,
                bundle
            )
        }
        // Search Articles functionality implementation
        var job: Job? = null
        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(DELAY_TIME)
                it.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.getSearchArticleList(it.toString())
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchArticlesFlow.collect {
                when (it) {
                    is Resource.Error -> {
                        hideProgressBar()
                        Toast.makeText(
                            requireContext(),
                            "An error occurred: ${it.message}",
                            Toast.LENGTH_LONG
                        ).show()
                        showErrorMessage(it.message.orEmpty())
                    }
                    is Resource.Loading -> {
                        if (binding.etSearch.text.isEmpty()) {
                            showProgressBar()
                        }
                    }
                    is Resource.Success -> {
                        hideProgressBar()
                        hideErrorMessage()
                        searchArticlesAdapter.differ.submitList(it.data)
                    }
                }
            }
        }
        binding.btnRetry.setOnClickListener {
            if (binding.etSearch.text.toString().isNotEmpty()) {
                viewModel.getSearchArticleList(binding.etSearch.text.toString())
            } else {
                hideErrorMessage()
            }
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage() {
        binding.itemErrorMessage.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        binding.itemErrorMessage.visibility = View.VISIBLE
        binding.tvErrorMessage.text = message
        isError = true
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getSearchArticleList(binding.etSearch.text.toString())
                isScrolling = false
            } else {
                binding.rvSearchArticles.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupRecyclerView() {
        searchArticlesAdapter = SearchArticlesAdapter()
        binding.rvSearchArticles.apply {
            adapter = searchArticlesAdapter
            addOnScrollListener(this@SearchArticleFragment.scrollListener)
        }
    }
}
