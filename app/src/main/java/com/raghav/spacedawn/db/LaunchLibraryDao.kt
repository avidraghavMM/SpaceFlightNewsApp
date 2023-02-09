package com.raghav.spacedawn.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchLibraryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLaunches(launches: List<LaunchLibraryResponseItem>)

    @Query("SELECT * from launches")
    fun getLaunches(): Flow<List<LaunchLibraryResponseItem>>
}
