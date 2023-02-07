package com.raghav.spacedawn.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raghav.spacedawn.R
import com.raghav.spacedawn.adapters.ArticlesAdapter
import com.raghav.spacedawn.databinding.FragmentArticlesListBinding
import com.raghav.spacedawn.ui.viewmodels.ArticlesListFragmentVM
import com.raghav.spacedawn.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.raghav.spacedawn.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesListFragment : Fragment(R.layout.fragment_articles_list) {

    private val viewModel by viewModels<ArticlesListFragmentVM>()
    private lateinit var articlesAdapter: ArticlesAdapter
    private lateinit var binding: FragmentArticlesListBinding

    companion object {
        const val TAG = "ArticlesListFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticlesListBinding.bind(view)
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.articlesFlow.collect {
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
                        showProgressBar()
                    }
                    is Resource.Success -> {
                        hideProgressBar()
                        hideErrorMessage()
                        if (it.data?.isEmpty() == true)
                            showErrorMessage(getString(R.string.failed_to_connect))
                        else
                            articlesAdapter.differ.submitList(it.data)
                    }
                }
            }
        }

        articlesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_articlesListFragment_to_articleDisplayFragment,
                bundle
            )
        }
        binding.btnRetry.setOnClickListener {
            viewModel.getArticlesList()
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
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getArticlesList()
                isScrolling = false
            } else {
                binding.rvArticles.setPadding(0, 0, 0, 0)
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
        articlesAdapter = ArticlesAdapter()
        binding.rvArticles.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@ArticlesListFragment.scrollListener)
        }
    }
}
