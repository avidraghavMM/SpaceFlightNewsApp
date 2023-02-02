package com.raghav.spacedawn.ui

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponse
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponse
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.repository.AppRepository
import com.raghav.spacedawn.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _articlesFlow =
        MutableStateFlow<Resource<List<ArticlesResponseItem>>>(Resource.Loading())
    val articlesFlow = _articlesFlow.asStateFlow()

    var articlesList: MutableList<ArticlesResponseItem>? = null
    var skipArticle = 0

    val searchArticleList: MutableLiveData<Resource<ArticlesResponse>> = MutableLiveData()
    var skipSearchArticle = 0
    var searchArticleResponse: ArticlesResponse? = null

    val launchesList: MutableLiveData<Resource<LaunchLibraryResponse>> = MutableLiveData()
    var launchResponse: LaunchLibraryResponse? = null
    var skipLaunches = 0

    init {
        getArticlesList()
        //getLaunchesList()
    }

    fun getArticlesList() {
        viewModelScope.launch {
            _articlesFlow.emit(Resource.Loading())
            repository.getArticles(skipArticle)
                .catch {
                    _articlesFlow.emit(Resource.Error("Error Occurred: ${it.localizedMessage}"))
                }
                .collect {
                    skipArticle += 10
                    if (articlesList.isNullOrEmpty()) {
                        articlesList = it.toMutableList()
                    } else {
                        articlesList?.addAll(it.toMutableList())
                    }
                    _articlesFlow.emit(Resource.Success(articlesList?.toList() ?: emptyList()))
                }
        }
    }

    fun getSearchArticleList(searchQuery: String) = viewModelScope.launch {
        //safeSearchArticleApiCall(searchQuery)
    }

    fun getLaunchesList() = viewModelScope.launch {
        //safeGetLauchesApiCall()
    }

//    private suspend fun safeSearchArticleApiCall(searchQuery: String) {
//        try {
//            if (hasInternetConnection()) {
//                searchArticleList.postValue(Resource.Loading())
//                val response = repository.searchArticle(searchQuery, skipSearchArticle)
//                searchArticleList.postValue(handleSearchResponse(response))
//            } else {
//                searchArticleList.postValue(Resource.Error("No Internet Connection"))
//            }
//        } catch (t: Throwable) {
//            when (t) {
//                is IOException -> searchArticleList.postValue(Resource.Error("Network Failure"))
//                else -> searchArticleList.postValue(Resource.Error("Conversion Error"))
//            }
//        }
//    }

//    private suspend fun safeGetArticleApiCall() {
//        try {
//            val response = repository.getArticles(skipArticle)
//            response.let {
//                skipArticle += 10
//                if (articlesResponse == null) {
//                    articlesResponse = it
//                } else {
//                    val oldArticles = articlesResponse?.list
//                    val newArticles = it.list ?: emptyList()
//                    oldArticles?.addAll(newArticles)
//                }
//                articlesList.postValue(Resource.Success(articlesResponse ?: it))
//            }
//        } catch (t: Throwable) {
//            when (t) {
//                is IOException -> articlesList.postValue(Resource.Error("Network Failure"))
//                else -> articlesList.postValue(Resource.Error("Conversion Error"))
//            }
//        }
//    }

//    private suspend fun safeGetLauchesApiCall() {
//        try {
//            if (hasInternetConnection()) {
//                launchesList.postValue(Resource.Loading())
//                val response = repository.getLaunches(skipLaunches)
//                launchesList.postValue(handleLaunchesResponse(response))
//            } else {
//                launchesList.postValue(Resource.Error("No Internet Connection"))
//            }
//        } catch (t: Throwable) {
//            when (t) {
//                is IOException -> articlesList.postValue(Resource.Error("Network Failure"))
//                else -> articlesList.postValue(Resource.Error("Conversion Error"))
//            }
//        }
//    }

    private fun handleLaunchesResponse(response: Response<LaunchLibraryResponse>): Resource<LaunchLibraryResponse>? {
        if (response.isSuccessful) {
            response.body()?.let {
                skipLaunches += 10
                if (launchResponse == null) {
                    launchResponse = it
                } else {
                    val oldArticles = launchResponse!!.results
                    val newArticles = it.results
                    oldArticles.addAll(newArticles)
                }
                return Resource.Success(launchResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

//    private fun handleSearchResponse(response: Response<ArticlesResponse>): Resource<ArticlesResponse> {
//        if (response.isSuccessful) {
//            response.body()?.let {
//                skipSearchArticle += 10
//                if (searchArticleResponse == null) {
//                    searchArticleResponse = it
//                } else {
//                    val oldArticles = searchArticleResponse?.list
//                    val newArticles = it.list ?: emptyList()
//                    oldArticles?.addAll(newArticles)
//                }
//                return Resource.Success(searchArticleResponse ?: it)
//            }
//        }
//        return Resource.Error(response.message())
//    }

    fun saveReminder(reminder: ReminderModelClass) = viewModelScope.launch {
        repository.insert(reminder)
    }

    fun getReminders() = repository.getAllReminders()

    fun getLaunchId(id: String) = repository.getId(id)

    fun deleteReminder(reminder: ReminderModelClass) = viewModelScope.launch {
        repository.deleteReminder(reminder)
    }
}
