package com.raghav.spacedawn.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.raghav.spacedawn.R
import com.raghav.spacedawn.adapters.ArticlesAdapter
import com.raghav.spacedawn.databinding.FragmentArticlesListBinding
import com.raghav.spacedawn.paging.LoaderAdapter
import com.raghav.spacedawn.ui.viewmodels.ArticlesListFragmentVM
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
            viewModel.articlesList.collect {
                articlesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
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
            articlesAdapter.refresh()
        }
    }

    private fun setupRecyclerView() {
        articlesAdapter = ArticlesAdapter()
        articlesAdapter.addLoadStateListener {
            showProgressBar(it.refresh is LoadState.Loading)
            showErrorMessage(
                it.refresh is LoadState.Error,
                getString(R.string.failed_to_connect)
            )
        }
        binding.rvArticles.apply {
            adapter = articlesAdapter
            adapter = articlesAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter {
                    articlesAdapter.refresh()
                },
                footer = LoaderAdapter {
                    articlesAdapter.refresh()
                }
            )
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showProgressBar(visibility: Boolean) {
        binding.paginationProgressBar.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }

    private fun showErrorMessage(visibility: Boolean, message: String = "") {
        if (visibility) {
            binding.itemErrorMessage.visibility = View.VISIBLE
            binding.tvErrorMessage.text = message
        } else {
            binding.itemErrorMessage.visibility = View.INVISIBLE
        }
    }
}
