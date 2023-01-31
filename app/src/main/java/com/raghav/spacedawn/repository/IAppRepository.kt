package com.raghav.spacedawn.repository

import androidx.lifecycle.LiveData
import com.raghav.spacedawn.db.ReminderModelClass
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponse
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponse
import retrofit2.Response

interface IAppRepository {
    suspend fun getArticles(skipArticles: Int): Response<ArticlesResponse>

    suspend fun searchArticle(searchQuery: String, skipArticles: Int): Response<ArticlesResponse>

    suspend fun getLaunches(skipLaunches: Int): Response<LaunchLibraryResponse>

    suspend fun insert(reminder: ReminderModelClass)
    fun getAllReminders(): LiveData<List<ReminderModelClass>>
    fun getId(id: String): Boolean

    suspend fun deleteReminder(reminder: ReminderModelClass)
}
