package com.raghav.spacedawn.ui.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.repository.AppRepository
import com.raghav.spacedawn.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesListFragmentVM @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel() {

    private val _launchesFlow =
        MutableStateFlow<Resource<PagingData<LaunchLibraryResponseItem>>>(Resource.Loading())
    val launchesFlow = _launchesFlow.asStateFlow()
    //private var skipLaunches = 0

    init {
        getLaunchesList()
    }

    fun getLaunchesList() = viewModelScope.launch {
        _launchesFlow.emit(Resource.Loading())
        repository.getLaunches()
            .cachedIn(this)
            .catch {
                _launchesFlow.emit(Resource.Error("Error Occurred: ${it.localizedMessage}"))
            }.collect { data ->
//                skipLaunches += 10
                _launchesFlow.emit(Resource.Success(data))
            }
    }

    fun saveReminder(reminder: ReminderModelClass) = viewModelScope.launch {
        repository.insert(reminder)
    }

    fun getReminders() = repository.getAllReminders()

    fun getLaunchId(id: String) = repository.getId(id)

    fun deleteReminder(reminder: ReminderModelClass) = viewModelScope.launch {
        repository.deleteReminder(reminder)
    }
}
