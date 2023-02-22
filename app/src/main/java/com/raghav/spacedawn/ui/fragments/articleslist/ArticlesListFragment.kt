package com.raghav.spacedawn.ui.fragments.articleslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.accompanist.themeadapter.material.MdcTheme
import com.raghav.spacedawn.R
import com.raghav.spacedawn.databinding.FragmentArticlesListBinding
import com.raghav.spacedawn.ui.viewmodels.ArticlesListFragmentVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesListFragment : Fragment(R.layout.fragment_articles_list) {

    private val viewModel by viewModels<ArticlesListFragmentVM>()
    private lateinit var binding: FragmentArticlesListBinding

    companion object {
        private const val TAG = "ArticlesListFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticlesListBinding.bind(view)

        binding.composeView.setContent {
            MdcTheme {
                ArticlesList(viewModel) {
                    val bundle = Bundle().apply {
                        putSerializable("article", it)
                    }
                    findNavController().navigate(
                        R.id.action_articlesListFragment_to_articleDisplayFragment,
                        bundle
                    )
                }
            }
        }
    }
}
