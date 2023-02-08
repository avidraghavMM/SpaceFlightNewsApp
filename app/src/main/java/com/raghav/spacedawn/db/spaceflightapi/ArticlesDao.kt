package com.raghav.spacedawn.db.spaceflightapi

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticles(articles: List<ArticlesResponseItem>)

    @Query("SELECT * from articles ORDER by publishedAt DESC")
    fun getArticlesByPublishedData(): PagingSource<Int, ArticlesResponseItem>

    @Query("DELETE from articles")
    suspend fun deleteAllArticles()
}
