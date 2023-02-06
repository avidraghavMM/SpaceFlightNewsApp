package com.raghav.spacedawn.ui.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesListFragmentVM @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel() {

    private var _launchesList: Flow<PagingData<LaunchLibraryResponseItem>> = emptyFlow()
    val launchesList: Flow<PagingData<LaunchLibraryResponseItem>> get() = _launchesList

    init {
        getLaunchesList()
    }

    fun getLaunchesList() {
        _launchesList = repository.getLaunches().cachedIn(viewModelScope)
    }

    fun saveReminder(reminder: ReminderModelClass) = viewModelScope.launch {
        repository.insert(reminder)
    }

    fun getLaunchId(id: String) = repository.getId(id)
}
