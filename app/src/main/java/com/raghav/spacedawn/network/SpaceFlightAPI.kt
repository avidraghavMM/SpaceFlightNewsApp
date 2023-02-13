package com.raghav.spacedawn.network

import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceFlightAPI {

    @GET("articles/count")
    suspend fun getArticlesCount(): Int

    @GET("articles")
    suspend fun getArticles(
        @Query("_title_contains")
        searchQuery: String? = null,
        @Query("_start")
        articlesToSkip: Int = 0
    ): ArticlesResponse
}
