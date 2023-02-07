package com.raghav.spacedawn.db.launchlibrary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raghav.spacedawn.models.LaunchLibraryKeys

@Dao
interface LaunchLibraryKeysDao {

    @Query("SELECT * FROM launch_library_keys WHERE id = :id")
    suspend fun getKeys(id: String): LaunchLibraryKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllKeys(keys: List<LaunchLibraryKeys>)

    @Query("DELETE FROM launch_library_keys")
    suspend fun deleteAllKeys()
}