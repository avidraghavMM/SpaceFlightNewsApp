package com.raghav.spacedawn.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * this table stores values of offset (both previous and next)
 * which is the query parameter of api service LaunchLibrary
 * corresponding to every launch saved in LaunchLibraryResponseItem
 * @see com.raghav.spacedawn.network.SpaceFlightAPI
 * @see com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
 */
@Entity(tableName = "articles_api_keys")
data class ArticlesApiKeys(
    @PrimaryKey
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
