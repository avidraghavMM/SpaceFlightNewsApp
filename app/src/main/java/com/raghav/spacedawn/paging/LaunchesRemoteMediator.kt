package com.raghav.spacedawn.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.raghav.spacedawn.db.AppDatabase
import com.raghav.spacedawn.models.LaunchLibraryKeys
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import com.raghav.spacedawn.network.LaunchLibrary
import com.raghav.spacedawn.utils.Constants
import com.raghav.spacedawn.utils.Helpers.Companion.isConnectedToNetwork

/**
 * interacts with both api and database
 * @param api Api Service for fetching data from network
 * @param database
 */
@OptIn(ExperimentalPagingApi::class)
class LaunchesRemoteMediator(
    private val api: LaunchLibrary, private val database: AppDatabase, private val context: Context
) : RemoteMediator<Int, LaunchLibraryResponseItem>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, LaunchLibraryResponseItem>
    ): MediatorResult {
        return try {
            if (!context.isConnectedToNetwork()) {
                return MediatorResult.Success(true)
            }
            val currentPage = when (loadType) {
                // initial display of data
                LoadType.REFRESH -> {
                    val keys = getKeyClosestToCurrentPosition(state)
                    keys?.nextPage?.minus(Constants.LAUNCHES_INCREMENT) ?: 0
                }
                // when RecyclerView is scrolled upwards
                LoadType.PREPEND -> {
                    val keys = getKeyForFirstItem(state)
                    val prevPage = keys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = keys != null
                    )
                    prevPage
                }
                // when RecyclerView is scrolled downwards
                LoadType.APPEND -> {
                    val keys = getKeyForLastItem(state)
                    val nextPage = keys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = keys != null
                    )
                    nextPage
                }
            }
            val response = api.getLaunches(currentPage)
            val endOfPaginationReached = response.next == "null"
            val prevPage =
                if (currentPage == 0) null else currentPage - Constants.LAUNCHES_INCREMENT
            val nextPage =
                if (endOfPaginationReached) null else currentPage + Constants.LAUNCHES_INCREMENT

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.getLaunchLibraryDao().deleteLaunches()
                    database.getLaunchLibraryKeysDao().deleteAllKeys()
                }
                database.getLaunchLibraryDao().saveLaunches(response.results)
                val keys = response.results.map { launch ->
                    LaunchLibraryKeys(
                        id = launch.id, prevPage = prevPage, nextPage = nextPage
                    )
                }
                database.getLaunchLibraryKeysDao().addAllKeys(keys)
            }

            MediatorResult.Success(endOfPaginationReached)

        } catch (e: java.lang.Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyClosestToCurrentPosition(
        state: PagingState<Int, LaunchLibraryResponseItem>
    ): LaunchLibraryKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.getLaunchLibraryKeysDao().getKeys(id = id)
            }
        }
    }

    private suspend fun getKeyForFirstItem(
        state: PagingState<Int, LaunchLibraryResponseItem>
    ): LaunchLibraryKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { launch ->
                database.getLaunchLibraryKeysDao().getKeys(id = launch.id)
            }
    }

    private suspend fun getKeyForLastItem(
        state: PagingState<Int, LaunchLibraryResponseItem>
    ): LaunchLibraryKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { launch ->
            database.getLaunchLibraryKeysDao().getKeys(id = launch.id)
        }
    }
}