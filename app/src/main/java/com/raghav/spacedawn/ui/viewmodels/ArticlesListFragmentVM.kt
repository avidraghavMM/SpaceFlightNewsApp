package com.raghav.spacedawn.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ArticlesListFragmentVM @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private lateinit var _articlesList: Flow<PagingData<ArticlesResponseItem>>
    val articlesList: Flow<PagingData<ArticlesResponseItem>> get() = _articlesList

    init {
        getArticlesList()
    }

    private fun getArticlesList() {
        _articlesList = repository.getArticles()
    }
}
