package com.raghav.spacedawn.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import com.raghav.spacedawn.network.LaunchLibrary
import com.raghav.spacedawn.utils.Constants
import javax.inject.Inject

/**
 * Paging Source for [LaunchLibrary] which handles loading the data from api
 * with appropriate query parameters
 * @param launchLibrary
 */
class LaunchesPagingSource @Inject constructor(private val launchLibrary: LaunchLibrary) :
    PagingSource<Int, LaunchLibraryResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, LaunchLibraryResponseItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(Constants.LAUNCHES_INCREMENT)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(Constants.LAUNCHES_INCREMENT)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LaunchLibraryResponseItem> {
        return try {
            val position = params.key ?: 0
            val response = launchLibrary.getLaunches(position)
            LoadResult.Page(
                data = response.results,
                prevKey = if (position == 0) null else position - Constants.LAUNCHES_INCREMENT,
                nextKey = position + Constants.LAUNCHES_INCREMENT
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}