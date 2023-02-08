package com.raghav.spacedawn.paging

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.raghav.spacedawn.db.AppDatabase
import com.raghav.spacedawn.models.ArticlesApiKeys
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.network.SpaceFlightAPI
import com.raghav.spacedawn.utils.Constants
import com.raghav.spacedawn.utils.Helpers.Companion.isConnectedToNetwork

/**
 * interacts with both api and database
 * @param api Api Service for fetching data from network
 * @param database
 */
@OptIn(ExperimentalPagingApi::class)
class ArticlesRemoteMediator(
    private val api: SpaceFlightAPI,
    private val database: AppDatabase,
    private val context: Context
) : RemoteMediator<Int, ArticlesResponseItem>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, ArticlesResponseItem>
    ): MediatorResult {
        return try {
            if (!context.isConnectedToNetwork()) {
                return MediatorResult.Success(true)
            }
            val currentPage = when (loadType) {
                // initial display of data
                LoadType.REFRESH -> {
                    val keys = getKeyClosestToCurrentPosition(state)
                    keys?.nextPage?.minus(Constants.ARTICLES_INCREMENT) ?: 0
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
            val response = api.getArticles(currentPage)
            val endOfPaginationReached = api.getArticlesCount() == currentPage
            Log.d("Test-articles", endOfPaginationReached.toString())
            val prevPage =
                if (currentPage == 0) null else currentPage - Constants.ARTICLES_INCREMENT
            val nextPage =
                if (endOfPaginationReached) null else currentPage + Constants.ARTICLES_INCREMENT

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.getSpaceFlightDao().deleteAllArticles()
                    database.getArticlesKeysDao().deleteAllKeys()
                }
                database.getSpaceFlightDao().saveArticles(response)
                val keys = response.map { article ->
                    ArticlesApiKeys(
                        id = article.id, prevPage = prevPage, nextPage = nextPage
                    )
                }
                database.getArticlesKeysDao().addAllKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyClosestToCurrentPosition(
        state: PagingState<Int, ArticlesResponseItem>
    ): ArticlesApiKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.getArticlesKeysDao().getKeys(id = id)
            }
        }
    }

    private suspend fun getKeyForFirstItem(
        state: PagingState<Int, ArticlesResponseItem>
    ): ArticlesApiKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                database.getArticlesKeysDao().getKeys(id = article.id)
            }
    }

    private suspend fun getKeyForLastItem(
        state: PagingState<Int, ArticlesResponseItem>
    ): ArticlesApiKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                database.getArticlesKeysDao().getKeys(id = article.id)
            }
    }
}