package com.raghav.spacedawn.ui.fragments.searcharticles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.raghav.spacedawn.R
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.ui.common.ItemLoadState
import com.raghav.spacedawn.ui.fragments.articleslist.ItemArticle
import com.raghav.spacedawn.ui.viewmodels.SearchArticleFragmentVM

@Composable
fun SearchArticleScreen(
    modifier: Modifier = Modifier,
    articleClickListener: (ArticlesResponseItem) -> Unit = {}
) {
    Surface(modifier = modifier, color = colorResource(id = R.color.colorPrimaryDark)) {
        val viewModel: SearchArticleFragmentVM = hiltViewModel()
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.searchQuery,
                onValueChange = viewModel::getSearchArticleList,
                label = {
                    Text(
                        text = stringResource(id = R.string.search),
                        color = colorResource(id = R.color.colorWhite)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    textColor = Color.White
                )
            )
            val articles = viewModel.searchArticlesList.collectAsLazyPagingItems()
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
}
