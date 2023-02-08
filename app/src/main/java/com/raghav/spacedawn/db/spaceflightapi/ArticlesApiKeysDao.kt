package com.raghav.spacedawn.db.spaceflightapi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raghav.spacedawn.models.ArticlesApiKeys

@Dao
interface ArticlesApiKeysDao {

    @Query("SELECT * FROM articles_api_keys WHERE id = :id")
    suspend fun getKeys(id: Int): ArticlesApiKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllKeys(keys: List<ArticlesApiKeys>)

    @Query("DELETE FROM articles_api_keys")
    suspend fun deleteAllKeys()
}