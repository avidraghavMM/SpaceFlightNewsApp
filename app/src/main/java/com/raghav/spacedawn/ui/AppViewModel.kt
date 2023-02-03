package com.raghav.spacedawn.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.repository.AppRepository
import com.raghav.spacedawn.ui.fragments.ArticlesListFragment
import com.raghav.spacedawn.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _articlesFlow =
        MutableStateFlow<Resource<List<ArticlesResponseItem>>>(Resource.Loading())
    val articlesFlow = _articlesFlow.asStateFlow()
    private var skipArticle = 0

    private val _searchArticlesFlow =
        MutableStateFlow<Resource<List<ArticlesResponseItem>>>(Resource.Success(emptyList()))
    val searchArticlesFlow = _searchArticlesFlow.asStateFlow()
    private var skipSearchArticle = 0

    private val _launchesFlow =
        MutableStateFlow<Resource<List<LaunchLibraryResponseItem>>>(Resource.Loading())
    val launchesFlow = _launchesFlow.asStateFlow()
    private var skipLaunches = 0

    init {
        getArticlesList()
        getLaunchesList()
    }

    fun getArticlesList() {
        viewModelScope.launch {
            _articlesFlow.emit(Resource.Loading())
            repository.getArticles(skipArticle)
                .catch {
                    _articlesFlow.emit(Resource.Error("Error Occurred: ${it.localizedMessage}"))
                }
                .collect { articlesList ->
                    Log.d(ArticlesListFragment.TAG, "vm-collector-triggered")
                    skipArticle += 10
                    _articlesFlow.emit(Resource.Success(articlesList))
                }
        }
    }

    fun getSearchArticleList(searchQuery: String) = viewModelScope.launch {
        repository.searchArticle(searchQuery, skipSearchArticle)
            .catch {
                _searchArticlesFlow.emit(Resource.Error("Error Occurred: ${it.localizedMessage}"))
            }
            .collect { searchArticlesList ->
                skipSearchArticle += 10
                _searchArticlesFlow.emit(
                    Resource.Success(searchArticlesList)
                )
            }
    }

    fun getLaunchesList() = viewModelScope.launch {
        repository.getLaunches(skipLaunches)
            .catch {
                _launchesFlow.emit(Resource.Error("Error Occurred: ${it.localizedMessage}"))
            }
            .collect { launchesList ->
                skipLaunches += 10
                _launchesFlow.emit(Resource.Success(launchesList))
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
