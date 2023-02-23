package com.raghav.spacedawn.ui.fragments.launcheslist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.raghav.spacedawn.R
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import com.raghav.spacedawn.ui.common.ItemLoadState
import com.raghav.spacedawn.ui.viewmodels.LaunchesListFragmentVM

@Composable
fun LaunchesList(
    viewModel: LaunchesListFragmentVM,
    modifier: Modifier = Modifier,
    addReminderListener: (LaunchLibraryResponseItem) -> Unit = {}
) {
    val launches = viewModel.launchesList.collectAsLazyPagingItems()
    LazyColumn(modifier) {
        items(items = launches, key = { it.id }) { launch ->
            launch?.let {
                ItemLaunch(
                    launch = it
                ) { clickedLaunch ->
                    addReminderListener(clickedLaunch)
                }
            }
        }

        when (val state = launches.loadState.refresh) {
            is LoadState.Error -> {
                item {
                    ItemLoadState(
                        message = state.error.localizedMessage.orEmpty(),
                        isProgressBarVisible = false,
                        isCtaVisible = true
                    ) {
                        launches.refresh()
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
        when (val state = launches.loadState.append) {
            is LoadState.Error -> {
                item {
                    ItemLoadState(
                        message = state.error.localizedMessage.orEmpty(),
                        isProgressBarVisible = false,
                        isCtaVisible = true
                    ) {
                        launches.refresh()
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
