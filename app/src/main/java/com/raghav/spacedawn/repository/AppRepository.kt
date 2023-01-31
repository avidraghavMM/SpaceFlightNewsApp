package com.raghav.spacedawn.repository

import com.raghav.spacedawn.db.ReminderDao
import com.raghav.spacedawn.db.ReminderModelClass
import com.raghav.spacedawn.network.LaunchLibrary
import com.raghav.spacedawn.network.SpaceFlightAPI

class AppRepository(
    private val dao: ReminderDao,
    private val spaceFlightApi: SpaceFlightAPI,
    private val launchLibraryApi: LaunchLibrary
) : IAppRepository {
    override suspend fun getArticles(skipArticles: Int) =
        spaceFlightApi.getArticles(skipArticles)

    override suspend fun searchArticle(searchQuery: String, skipArticles: Int) =
        spaceFlightApi.searchArticles(searchQuery, skipArticles)

    override suspend fun getLaunches(skipLaunches: Int) =
        launchLibraryApi.getLaunches(skipLaunches)

    override suspend fun insert(reminder: ReminderModelClass) =
        dao.saveReminder(reminder)

    override fun getAllReminders() = dao.getAllReminders()
    override fun getId(id: String) = dao.exists(id)
    override suspend fun deleteReminder(reminder: ReminderModelClass) =
        dao.deleteReminder(reminder)
}
