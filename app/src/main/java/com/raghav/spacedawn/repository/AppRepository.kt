package com.raghav.spacedawn.repository

import android.content.Context
import com.raghav.spacedawn.db.ReminderDao
import com.raghav.spacedawn.db.SpaceFlightDao
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.network.LaunchLibrary
import com.raghav.spacedawn.network.SpaceFlightAPI
import com.raghav.spacedawn.utils.Helpers.Companion.isConnectedToNetwork
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val reminderDao: ReminderDao,
    private val spaceFlightDao: SpaceFlightDao,
    private val spaceFlightApi: SpaceFlightAPI,
    private val launchLibraryApi: LaunchLibrary
) {

    /**
     * Returns a list of articles from database after saving the api response
     * in database if Internet connection available.
     * In case there is no data in database an empty list is returned
     * */
    suspend fun getArticles(skipArticles: Int): Flow<List<ArticlesResponseItem>> {
        if (appContext.isConnectedToNetwork()) {
            spaceFlightDao.saveArticles(spaceFlightApi.getArticles(skipArticles))
        }
        return spaceFlightDao.getArticles()
    }

    suspend fun searchArticle(searchQuery: String, skipArticles: Int) =
        spaceFlightApi.searchArticles(searchQuery, skipArticles)

    suspend fun getLaunches(skipLaunches: Int) =
        launchLibraryApi.getLaunches(skipLaunches)

    suspend fun insert(reminder: ReminderModelClass) = reminderDao.saveReminder(reminder)

    fun getAllReminders() = reminderDao.getAllReminders()
    fun getId(id: String) = reminderDao.exists(id)
    suspend fun deleteReminder(reminder: ReminderModelClass) = reminderDao.deleteReminder(reminder)
}
