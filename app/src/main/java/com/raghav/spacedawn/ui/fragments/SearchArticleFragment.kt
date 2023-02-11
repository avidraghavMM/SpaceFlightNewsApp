package com.raghav.spacedawn.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.raghav.spacedawn.R
import com.raghav.spacedawn.adapters.ArticlesAdapter
import com.raghav.spacedawn.databinding.FragmentSearchArticleBinding
import com.raghav.spacedawn.paging.LoaderAdapter
import com.raghav.spacedawn.ui.viewmodels.SearchArticleFragmentVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchArticleFragment : Fragment(R.layout.fragment_search_article) {

    private val viewModel by viewModels<SearchArticleFragmentVM>()
    private lateinit var articlesAdapter: ArticlesAdapter
    private lateinit var binding: FragmentSearchArticleBinding

    companion object {
        private const val TAG = "SearchArticleFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchArticleBinding.bind(view)
        setupRecyclerView()

        articlesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchArticleFragment_to_articleDisplayFragment,
                bundle
            )
        }

        binding.etSearch.addTextChangedListener {
            viewModel.setSearchQuery(it.toString())
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            articlesAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && articlesAdapter.itemCount == 0

                // only show recylerview if the list is not empty
                binding.rvSearchArticles.isVisible = !isListEmpty

                // show progessbar during initial load or refresh
                showProgressBar(loadState.source.refresh is LoadState.Loading)

                // show error message message if initial load or refresh fails
                showErrorMessage(
                    loadState.source.refresh is LoadState.Error,
                    getString(R.string.failed_to_connect)
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchArticlesList.collect {
                it?.let {
                    articlesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
        }

        binding.btnRetry.setOnClickListener {
            articlesAdapter.retry()
        }
    }

    private fun showProgressBar(show: Boolean) {
        binding.paginationProgressBar.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    private fun showErrorMessage(show: Boolean, message: String = "") {
        if (show) {
            binding.itemErrorMessage.visibility = View.VISIBLE
            binding.tvErrorMessage.text = message
        } else {
            binding.itemErrorMessage.visibility = View.INVISIBLE
        }
    }

    private fun setupRecyclerView() {
        articlesAdapter = ArticlesAdapter()
        articlesAdapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter { articlesAdapter.retry() },
            footer = LoaderAdapter { articlesAdapter.retry() }
        )
        binding.rvSearchArticles.adapter = articlesAdapter
    }
}
