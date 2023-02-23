package com.raghav.spacedawn.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchArticleFragmentVM @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private var _searchArticlesList: Flow<PagingData<ArticlesResponseItem>> =
        emptyFlow()
    val searchArticlesList: Flow<PagingData<ArticlesResponseItem>> get() = _searchArticlesList

    var searchQuery by mutableStateOf("")
        private set

    init {
        getSearchArticleList(searchQuery)
    }

    fun getSearchArticleList(query: String) {
        searchQuery = query
        viewModelScope.launch {
            repository.searchArticle(searchQuery)
                .cachedIn(this)
                .collect {
                    _searchArticlesList = flowOf(it)
                }
        }
    }
}
