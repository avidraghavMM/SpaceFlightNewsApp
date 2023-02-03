package com.raghav.spacedawn.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindersListFragmentVM @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    fun getReminders() = repository.getAllReminders()

    fun deleteReminder(reminder: ReminderModelClass) = viewModelScope.launch {
        repository.deleteReminder(reminder)
    }
}
