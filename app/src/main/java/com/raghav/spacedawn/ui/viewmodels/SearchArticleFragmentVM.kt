package com.raghav.spacedawn.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.repository.AppRepository
import com.raghav.spacedawn.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchArticleFragmentVM @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private var _searchArticlesList: MutableStateFlow<PagingData<ArticlesResponseItem>?> =
        MutableStateFlow(null)
    val searchArticlesList: StateFlow<PagingData<ArticlesResponseItem>?> get() = _searchArticlesList

    var query = MutableStateFlow("")

    init {
        viewModelScope.launch {
            query
                .debounce(Constants.SEARCH_DELAY_TIME)
                .collect {
                    getSearchArticleList(it)
                }
        }
    }

    private fun getSearchArticleList(searchQuery: String) {
        viewModelScope.launch {
            repository.searchArticle(searchQuery)
                .cachedIn(this)
                .collect {
                    _searchArticlesList.emit(it)
                }
        }
    }

    fun setSearchQuery(searchQuery: String) {
        viewModelScope.launch {
            query.emit(searchQuery)
        }
    }
}
