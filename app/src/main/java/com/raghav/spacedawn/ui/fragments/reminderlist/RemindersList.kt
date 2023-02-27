package com.raghav.spacedawn.ui.fragments.reminderlist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.raghav.spacedawn.models.reminder.ReminderModelClass

@Composable
fun RemindersList(
    remindersList: List<ReminderModelClass>,
    modifier: Modifier = Modifier,
    cancelReminderListener: (ReminderModelClass) -> Unit = {}
) {
    LazyColumn(modifier) {
        items(items = remindersList, key = { it.id }) { reminder ->
            ItemReminder(
                reminder
            ) {
                cancelReminderListener(it)
            }
        }
    }
}
