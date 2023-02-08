package com.raghav.spacedawn.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesListFragmentVM @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private lateinit var _launchesList: Flow<PagingData<LaunchLibraryResponseItem>>
    val launchesList: Flow<PagingData<LaunchLibraryResponseItem>> get() = _launchesList

    init {
        getLaunchesList()
    }

    private fun getLaunchesList() {
        _launchesList = repository.getLaunches()
    }

    fun saveReminder(reminder: ReminderModelClass) = viewModelScope.launch {
        repository.insert(reminder)
    }

    fun getLaunchId(id: String) = repository.getId(id)
}
