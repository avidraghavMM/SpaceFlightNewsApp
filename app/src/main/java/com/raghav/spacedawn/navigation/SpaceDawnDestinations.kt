package com.raghav.spacedawn.navigation

import androidx.annotation.DrawableRes
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.raghav.spacedawn.R

interface Destination {
    @get:DrawableRes
    val icon: Int
    val route: String
    val label: String
}

object ArticlesList : Destination {
    override val icon = R.drawable.ic_baseline_new_24
    override val route = "articles_list"
    override val label = "Latest News"
}

object SearchArticlesList : Destination {
    override val icon = R.drawable.ic_baseline_search_24
    override val route = "search_articles_list"
    override val label = "Search"
}

object LaunchesList : Destination {
    override val icon = R.drawable.ic_launch
    override val route = "launches_list"
    override val label = "Launches"
}

object RemindersList : Destination {
    override val icon = R.drawable.ic_reminder
    override val route = "reminders_list"
    override val label = "Reminders"
}

// sample destination with support of arguments

object ArticleDisplay : Destination {
    override val icon = R.drawable.icon
    override val route = "article_display"
    const val articleUrlArg = "article_url"
    val routeWithArgs = "$route/{$articleUrlArg}"
    val arguments = listOf(
        navArgument(articleUrlArg) { type = NavType.StringType }
    )
    override val label = "Article"
}

val bottomBarScreens = listOf(ArticlesList, SearchArticlesList, LaunchesList, RemindersList)
