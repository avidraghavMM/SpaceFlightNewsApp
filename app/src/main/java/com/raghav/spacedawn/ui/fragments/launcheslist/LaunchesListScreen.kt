package com.raghav.spacedawn.ui.fragments.launcheslist

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.raghav.spacedawn.ui.common.ItemLoadState
import com.raghav.spacedawn.ui.viewmodels.LaunchesListFragmentVM

@Composable
fun LaunchesListScreen(
    modifier: Modifier = Modifier,
    reminderSetListener: () -> Unit
) {
    // without the fillMaxSize modifier the surface is not
    // occupying complete width in this case
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = colorResource(id = R.color.colorPrimaryDark)
    ) {
        val viewModel: LaunchesListFragmentVM = hiltViewModel()
        val launches = viewModel.launchesList.collectAsLazyPagingItems()

        LazyColumn {
            items(items = launches, key = { it.id }) { launch ->
                launch?.let {
                    ItemLaunch(
                        launch = it
                    ) { clickedLaunch ->
                        viewModel.setReminder(clickedLaunch)
                        reminderSetListener()
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
}
