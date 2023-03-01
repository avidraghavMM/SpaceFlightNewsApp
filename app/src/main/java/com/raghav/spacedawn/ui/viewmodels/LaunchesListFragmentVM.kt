package com.raghav.spacedawn.ui.viewmodels

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.repository.AppRepository
import com.raghav.spacedawn.utils.AlarmBroadCastReciever
import com.raghav.spacedawn.utils.Constants
import com.raghav.spacedawn.utils.Helpers.Companion.formatTo
import com.raghav.spacedawn.utils.Helpers.Companion.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class LaunchesListFragmentVM @Inject constructor(
    private val repository: AppRepository,
    @ApplicationContext
    private val context: Context
) : ViewModel() {

    private var _launchesList: Flow<PagingData<LaunchLibraryResponseItem>> = emptyFlow()
    val launchesList: Flow<PagingData<LaunchLibraryResponseItem>> get() = _launchesList

    init {
        getLaunchesList()
    }

    private fun getLaunchesList() {
        _launchesList = repository.getLaunches()
    }

    private suspend fun insertReminderInDb(reminder: ReminderModelClass) = viewModelScope.launch {
        repository.insert(reminder)
    }

    private fun getLaunchId(id: String) = repository.getId(id)

    fun setReminder(launch: LaunchLibraryResponseItem) {
        viewModelScope.launch(Dispatchers.Default) {
            val dateTime = launch.net.toDate(Constants.LAUNCH_DATE_INPUT_FORMAT)
            val timeToSetAlarm: Long = dateTime.time - Constants.MinutestoMiliseconds
            setAlarm(timeToSetAlarm, System.currentTimeMillis().toInt(), launch)
        }
    }

    private suspend fun setAlarm(
        timeInMilliseconds: Long,
        pendingIntentId: Int,
        launch: LaunchLibraryResponseItem
    ) {
        val nameOfLaunch = launch.name
        val idOfLaucnh = launch.id
        val imageUrl = launch.image
        val dateTimeOfLaunch = launch.net.toDate(Constants.LAUNCH_DATE_INPUT_FORMAT).formatTo(
            Constants.DATE_OUTPUT_FORMAT
        )

        val am: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(context, AlarmBroadCastReciever::class.java)
        val pi = PendingIntent.getBroadcast(
            context,
            pendingIntentId,
            i,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val reminder = ReminderModelClass(
            idOfLaucnh,
            nameOfLaunch,
            dateTimeOfLaunch,
            pendingIntentId,
            Constants.STATUS_SET,
            imageUrl
        )

        insertReminderInDb(reminder)
        am.setExact(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pi)
    }
}
