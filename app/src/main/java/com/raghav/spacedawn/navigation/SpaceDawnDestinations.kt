package com.raghav.spacedawn.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.raghav.spacedawn.R

interface Destination {
    val icon: Int
    val route: String
}

object ArticlesList : Destination {
    override val icon = R.drawable.ic_baseline_new_24
    override val route = "articles_list"
}

object SearchArticlesList : Destination {
    override val icon = R.drawable.ic_baseline_search_24
    override val route = "search_articles_list"
}

object LaunchesList : Destination {
    override val icon = R.drawable.ic_launch
    override val route = "launches_list"
}

object RemindersList : Destination {
    override val icon = R.drawable.ic_reminder
    override val route = "launches_list"
}

object ArticleDisplay : Destination {
    override val icon = R.drawable.icon
    override val route = "article_display"
    const val articleUrlArg = "article_url"
    val routeWithArgs = "$route/{$articleUrlArg}"
    val arguments = listOf(
        navArgument(articleUrlArg) { type = NavType.StringType }
    )
}

val bottomBarScreens = listOf(ArticlesList, SearchArticlesList, LaunchesList, RemindersList)
