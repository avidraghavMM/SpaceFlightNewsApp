package com.raghav.spacedawn.ui.fragments.reminderlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.raghav.spacedawn.R
import com.raghav.spacedawn.ui.viewmodels.RemindersListFragmentVM

@Composable
fun RemindersListScreen(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = colorResource(id = R.color.colorPrimaryDark)
    ) {
        val viewModel: RemindersListFragmentVM = hiltViewModel()
        val reminders = viewModel.getReminders().observeAsState().value

        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = reminders.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.set_reminders_from_launches_tab),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = reminders.orEmpty(), key = { it.id }) { reminder ->
                ItemReminder(
                    reminder
                ) {
                    viewModel.cancelReminder(it)
                }
            }
        }
    }
}
