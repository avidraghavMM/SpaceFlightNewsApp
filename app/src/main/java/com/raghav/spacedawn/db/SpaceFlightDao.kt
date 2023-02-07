package com.raghav.spacedawn.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface SpaceFlightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticles(articles: List<ArticlesResponseItem>)

    @Query("SELECT * from articles ORDER by publishedAt DESC")
    fun getArticlesByPublishedData(): Flow<List<ArticlesResponseItem>>

    @Query("DELETE from articles")
    suspend fun deleteAllArticles()
}
