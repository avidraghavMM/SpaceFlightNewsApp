package com.raghav.spacedawn.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.raghav.spacedawn.R
import com.raghav.spacedawn.db.AppDatabase
import com.raghav.spacedawn.models.ArticlesApiKeys
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.network.SpaceFlightAPI
import com.raghav.spacedawn.utils.Constants
import com.raghav.spacedawn.utils.Helpers.Companion.isConnectedToNetwork

/**
 * interacts with both api and database
 * @param api Api Service for fetching data from network
 * @param database instance of app's database
 */
@OptIn(ExperimentalPagingApi::class)
class ArticlesRemoteMediator(
    private val api: SpaceFlightAPI,
    private val database: AppDatabase,
    private val context: Context
) : RemoteMediator<Int, ArticlesResponseItem>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticlesResponseItem>
    ): MediatorResult {
        return try {
            if (!context.isConnectedToNetwork()) {
                return MediatorResult.Error(Throwable(message = context.getString(R.string.failed_to_connect)))
            }
            val currentPage = when (loadType) {
                // initial display of data
                LoadType.REFRESH -> {
                    val keys = getKeyClosestToCurrentPosition(state)
                    keys?.nextPage?.minus(Constants.SKIP_ARTICLES_COUNT) ?: 0
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
            val response = api.getArticles(articlesToSkip = currentPage)

            // api call to get the articles available at the backend and hence calculate
            // whether further pagination can be done
            val endOfPaginationReached = api.getArticlesCount() == currentPage

            // query param to fetch the preceding page is required when
            // RecyclerView is scrolled upwards
            val prevPage =
                if (currentPage == 0) null else currentPage - Constants.SKIP_ARTICLES_COUNT

            // query param to fetch the succeeding page is required when
            // RecyclerView is scrolled downwards
            val nextPage =
                if (endOfPaginationReached) null else currentPage + Constants.SKIP_ARTICLES_COUNT

            database.withTransaction {
                // invalidate both keys and articles database during initial fetch
                // or when some internal error occurs
                if (loadType == LoadType.REFRESH) {
                    database.getSpaceFlightDao().deleteAllArticles()
                    database.getArticlesKeysDao().deleteAllKeys()
                }

                // save articles in database
                database.getSpaceFlightDao().saveArticles(response)

                // a separate table corresponding to query params need
                // to be kept so that the state of pagination can be persisted
                // across app sessions
                val keys = response.map { article ->
                    ArticlesApiKeys(
                        id = article.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                database.getArticlesKeysDao().addAllKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    /**
     *  this method will only be called during initial fetch.
     * anchorPosition is maintained by Paging Library to figure out
     * which page to load next.
     */
    private suspend fun getKeyClosestToCurrentPosition(
        state: PagingState<Int, ArticlesResponseItem>
    ): ArticlesApiKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.getArticlesKeysDao().getKeys(id = id)
            }
        }
    }

    /**
     * this method fetches the query_param/key from keys table
     * to fetch a page preceding the current page
     */
    private suspend fun getKeyForFirstItem(
        state: PagingState<Int, ArticlesResponseItem>
    ): ArticlesApiKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                database.getArticlesKeysDao().getKeys(id = article.id)
            }
    }

    /**
     * this method fetches the query_param/key from keys table
     * to fetch a page succeeding the current page
     */
    private suspend fun getKeyForLastItem(
        state: PagingState<Int, ArticlesResponseItem>
    ): ArticlesApiKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                database.getArticlesKeysDao().getKeys(id = article.id)
            }
    }
}
