package com.raghav.spacedawn.ui.fragments.articleslist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.raghav.spacedawn.R
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.ui.common.ItemLoadState
import com.raghav.spacedawn.ui.viewmodels.ArticlesListVM

@Composable
fun ArticlesListScreen(
    modifier: Modifier = Modifier,
    articleClickListener: (ArticlesResponseItem) -> Unit = {}
) {
    Surface(modifier = modifier, color = colorResource(id = R.color.colorPrimaryDark)) {
        val viewModel: ArticlesListVM = hiltViewModel()
        val articles = viewModel.articlesList.collectAsLazyPagingItems()
        LazyColumn {
            items(items = articles, key = { it.id }) { article ->
                article?.let {
                    ItemArticle(
                        article = it
                    ) { clickedArticle ->
                        articleClickListener(clickedArticle)
                    }
                }
            }

            when (val state = articles.loadState.refresh) {
                is LoadState.Error -> {
                    item {
                        ItemLoadState(
                            message = state.error.localizedMessage.orEmpty(),
                            isProgressBarVisible = false,
                            isCtaVisible = true
                        ) {
                            articles.refresh()
                        }
                    }
                }
                LoadState.Loading -> {
                    item {
                        ItemLoadState(
                            message = stringResource(id = R.string.refreshing),
                            isProgressBarVisible = true,
                            isCtaVisible = false
                        )
                    }
                }
                is LoadState.NotLoading -> Unit
            }
            when (val state = articles.loadState.append) {
                is LoadState.Error -> {
                    item {
                        ItemLoadState(
                            message = state.error.localizedMessage.orEmpty(),
                            isProgressBarVisible = false,
                            isCtaVisible = true
                        ) {
                            articles.refresh()
                        }
                    }
                }
                LoadState.Loading -> {
                    item {
                        ItemLoadState(
                            message = stringResource(id = R.string.loading),
                            isProgressBarVisible = true,
                            isCtaVisible = false
                        )
                    }
                }
                is LoadState.NotLoading -> Unit
            }
        }
    }
}
