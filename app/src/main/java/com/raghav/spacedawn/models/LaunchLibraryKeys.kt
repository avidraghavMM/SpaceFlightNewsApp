package com.raghav.spacedawn.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * this table stores values of offset (both previous and next)
 * which is the query parameter of api service LaunchLibrary
 * corresponding to every launch saved in LaunchLibraryResponseItem
 * @see com.raghav.spacedawn.network.LaunchLibrary
 * @see com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
 */
@Entity(tableName = "launch_library_keys")
data class LaunchLibraryKeys(
    @PrimaryKey
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
