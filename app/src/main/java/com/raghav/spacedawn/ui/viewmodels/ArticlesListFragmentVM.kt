package com.raghav.spacedawn.ui.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
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
class ArticlesListFragmentVM @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel() {

    private val _articlesFlow =
        MutableStateFlow<Resource<List<ArticlesResponseItem>>>(Resource.Loading())
    val articlesFlow = _articlesFlow.asStateFlow()
    private var skipArticle = 0

    init {
        getArticlesList()
    }

    fun getArticlesList() {
        job?.cancel()
        job = viewModelScope.launch {
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
}
