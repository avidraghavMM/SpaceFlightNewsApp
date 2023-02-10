package com.raghav.spacedawn.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.raghav.spacedawn.db.AppDatabase
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.network.LaunchLibrary
import com.raghav.spacedawn.network.SpaceFlightAPI
import com.raghav.spacedawn.paging.ArticlesRemoteMediator
import com.raghav.spacedawn.paging.LaunchesRemoteMediator
import com.raghav.spacedawn.utils.Helpers.Companion.isConnectedToNetwork
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val spaceFlightApi: SpaceFlightAPI,
    private val launchLibrary: LaunchLibrary,
    private val database: AppDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getArticles(): Flow<PagingData<ArticlesResponseItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10, maxSize = 30),
            remoteMediator = ArticlesRemoteMediator(spaceFlightApi, database, appContext),
            pagingSourceFactory = { database.getSpaceFlightDao().getArticlesByPublishedData() }
        ).flow
    }

    suspend fun searchArticle(
        searchQuery: String,
        skipArticles: Int
    ): Flow<List<ArticlesResponseItem>> = flow {
        val result = if (appContext.isConnectedToNetwork()) {
            spaceFlightApi.searchArticles(searchQuery, skipArticles)
        } else {
            emptyList()
        }
        emit(result)
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getLaunches(): Flow<PagingData<LaunchLibraryResponseItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10, maxSize = 30),
            remoteMediator = LaunchesRemoteMediator(launchLibrary, database, appContext),
            pagingSourceFactory = { database.getLaunchLibraryDao().getLaunches() }
        ).flow
    }

    suspend fun insert(reminder: ReminderModelClass) =
        database.getRemindersDao().saveReminder(reminder)

    fun getAllReminders() = database.getRemindersDao().getAllReminders()
    fun getId(id: String) = database.getRemindersDao().exists(id)
    suspend fun deleteReminder(reminder: ReminderModelClass) =
        database.getRemindersDao().deleteReminder(reminder)
}
