package com.raghav.spacedawn.db.launchlibrary

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem

@Dao
interface LaunchLibraryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLaunches(launches: List<LaunchLibraryResponseItem>)

    @Query("SELECT * from launches")
    fun getLaunches(): PagingSource<Int, LaunchLibraryResponseItem>

    @Query("DELETE FROM launches")
    suspend fun deleteLaunches()
}
