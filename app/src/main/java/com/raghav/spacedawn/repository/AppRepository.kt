package com.raghav.spacedawn.repository

import com.raghav.spacedawn.db.ReminderDao
import com.raghav.spacedawn.db.ReminderModelClass
import com.raghav.spacedawn.network.LaunchLibrary
import com.raghav.spacedawn.network.SpaceFlightAPI
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val dao: ReminderDao,
    private val spaceFlightApi: SpaceFlightAPI,
    private val launchLibraryApi: LaunchLibrary
) {
    suspend fun getArticles(skipArticles: Int) =
        spaceFlightApi.getArticles(skipArticles)

    suspend fun searchArticle(searchQuery: String, skipArticles: Int) =
        spaceFlightApi.searchArticles(searchQuery, skipArticles)

    suspend fun getLaunches(skipLaunches: Int) =
        launchLibraryApi.getLaunches(skipLaunches)

    suspend fun insert(reminder: ReminderModelClass) =
        dao.saveReminder(reminder)

    fun getAllReminders() = dao.getAllReminders()
    fun getId(id: String) = dao.exists(id)
    suspend fun deleteReminder(reminder: ReminderModelClass) =
        dao.deleteReminder(reminder)
}
