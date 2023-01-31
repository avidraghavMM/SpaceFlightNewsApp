package com.raghav.spacedawn.repository

import com.raghav.spacedawn.db.ReminderDao
import com.raghav.spacedawn.db.ReminderModelClass
import com.raghav.spacedawn.network.RetrofitInstance

class AppRepository(
    private val dao: ReminderDao
) : IAppRepository {
    override suspend fun getArticles(skipArticles: Int) =
        RetrofitInstance.api_spaceflight.getArticles(skipArticles)

    override suspend fun searchArticle(searchQuery: String, skipArticles: Int) =
        RetrofitInstance.api_spaceflight.searchArticles(searchQuery, skipArticles)

    override suspend fun getLaunches(skipLaunches: Int) =
        RetrofitInstance.api_launchlibrary.getLaunches(skipLaunches)

    override suspend fun insert(reminder: ReminderModelClass) =
        dao.saveReminder(reminder)

    override fun getAllReminders() = dao.getAllReminders()
    override fun getId(id: String) = dao.exists(id)
    override suspend fun deleteReminder(reminder: ReminderModelClass) =
        dao.deleteReminder(reminder)
}
