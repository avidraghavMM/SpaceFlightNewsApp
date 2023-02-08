package com.raghav.spacedawn.network

import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponse
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceFlightAPI {

    @GET("articles")
    suspend fun getArticles(
        @Query("_start")
        articlesToSkip: Int = 0
    ): ArticlesResponse

    @GET("articles/count")
    suspend fun getArticlesCount(
    ): Int

    @GET("articles")
    suspend fun searchArticles(
        @Query("_title_contains")
        searchQuery: String,
        @Query("_start")
        articlesToSkip: Int = 0
    ): List<ArticlesResponseItem>
}
