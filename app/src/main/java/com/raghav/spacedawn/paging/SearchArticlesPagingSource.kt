package com.raghav.spacedawn.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.network.SpaceFlightAPI
import com.raghav.spacedawn.utils.Constants

/**
 * responsible for retrieving the date from network
 * @param api Api Service for fetching data from network
 * @param query specific string corresponding to which network request will be made
 */
class SearchArticlesPagingSource(
    private val api: SpaceFlightAPI,
    private val query: String
) : PagingSource<Int, ArticlesResponseItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesResponseItem> {
        val position = params.key ?: 0
        val apiQuery = query

        return try {
            val articlesList = api.getArticles(searchQuery = apiQuery, articlesToSkip = position)
            val nextKey = if (articlesList.isEmpty()) {
                null
            } else {
                position + Constants.SKIP_ARTICLES_COUNT
            }
            LoadResult.Page(
                data = articlesList,
                prevKey = if (position == 0) null else position - Constants.SKIP_ARTICLES_COUNT,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, ArticlesResponseItem>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(Constants.SKIP_ARTICLES_COUNT)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(Constants.SKIP_ARTICLES_COUNT)
        }
    }
}
