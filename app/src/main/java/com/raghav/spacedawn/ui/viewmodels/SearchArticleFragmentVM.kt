package com.raghav.spacedawn.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.repository.AppRepository
import com.raghav.spacedawn.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchArticleFragmentVM @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _searchArticlesFlow =
        MutableStateFlow<Resource<List<ArticlesResponseItem>>>(Resource.Success(emptyList()))
    val searchArticlesFlow = _searchArticlesFlow.asStateFlow()
    private var skipSearchArticle = 0

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
}
