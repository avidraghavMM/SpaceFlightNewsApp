package com.raghav.spacedawn.ui.viewmodels

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.repository.AppRepository
import com.raghav.spacedawn.utils.AlarmBroadCastReciever
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class RemindersListFragmentVM @Inject constructor(
    private val repository: AppRepository,
    @ApplicationContext
    private val context: Context
) : ViewModel() {

    fun getReminders() = repository.getAllReminders()

    private suspend fun deleteReminder(reminder: ReminderModelClass) = viewModelScope.launch {
        repository.deleteReminder(reminder)
    }

    fun cancelReminder(reminder: ReminderModelClass) {
        viewModelScope.launch {
            val am: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val i = Intent(context, AlarmBroadCastReciever::class.java)
            val pi = PendingIntent.getBroadcast(
                context,
                reminder.pendingIntentId,
                i,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            am.cancel(pi)
            pi.cancel()
            deleteReminder(reminder)
        }
    }
}
