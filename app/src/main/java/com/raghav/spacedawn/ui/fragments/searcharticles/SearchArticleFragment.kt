package com.raghav.spacedawn.ui.fragments.searcharticles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.raghav.spacedawn.R
import com.raghav.spacedawn.databinding.FragmentSearchArticleBinding
import com.raghav.spacedawn.ui.viewmodels.SearchArticleFragmentVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchArticleFragment : Fragment(R.layout.fragment_search_article) {

    private val viewModel by viewModels<SearchArticleFragmentVM>()
    private lateinit var binding: FragmentSearchArticleBinding

    companion object {
        private const val TAG = "SearchArticleFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchArticleBinding.bind(view)

        binding.composeView.setContent {
            SearchArticleScreen(
                viewModel = viewModel
            ) {
                val bundle = Bundle().apply {
                    putSerializable("article", it)
                }
                findNavController().navigate(
                    R.id.action_searchArticleFragment_to_articleDisplayFragment,
                    bundle
                )
            }
        }
    }
}
