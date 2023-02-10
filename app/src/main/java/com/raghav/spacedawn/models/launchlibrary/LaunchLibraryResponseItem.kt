package com.raghav.spacedawn.models.launchlibrary

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "launches")
data class LaunchLibraryResponseItem(
    @PrimaryKey
    val id: String,
    val failreason: String,
    val holdreason: String,
    val image: String?,
    val last_updated: String,
    val launch_service_provider: LaunchServiceProvider,
    val name: String,
    val net: String,
    val probability: Int,
    val rocket: Rocket,
    val slug: String,
    val status: Status,
    val url: String,
    val webcast_live: Boolean,
    val window_end: String,
    val window_start: String
) : Serializable
