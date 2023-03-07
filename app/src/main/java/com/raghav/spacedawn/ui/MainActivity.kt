package com.raghav.spacedawn.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.themeadapter.material.MdcTheme
import com.raghav.spacedawn.R
import com.raghav.spacedawn.models.NavDrawerMenuItem
import com.raghav.spacedawn.navigation.ArticlesList
import com.raghav.spacedawn.navigation.LaunchesList
import com.raghav.spacedawn.navigation.RemindersList
import com.raghav.spacedawn.navigation.SearchArticlesList
import com.raghav.spacedawn.navigation.bottomBarScreens
import com.raghav.spacedawn.ui.common.BottomNavigationBar
import com.raghav.spacedawn.ui.common.DrawerBody
import com.raghav.spacedawn.ui.common.DrawerHeader
import com.raghav.spacedawn.ui.common.TopAppBar
import com.raghav.spacedawn.ui.fragments.articleslist.ArticlesListScreen
import com.raghav.spacedawn.ui.fragments.launcheslist.LaunchesListScreen
import com.raghav.spacedawn.ui.fragments.reminderlist.RemindersListScreen
import com.raghav.spacedawn.ui.fragments.searcharticles.SearchArticleScreen
import com.raghav.spacedawn.utils.Constants
import com.raghav.spacedawn.utils.Helpers.Companion.navigateSingleTopTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val navDrawerMenuList = listOf(
        NavDrawerMenuItem(
            id = Constants.ID_VIEW_GITHUB_REPO,
            icon = R.drawable.ic_githubrepo,
            title = R.string.github_repository
        ),
        NavDrawerMenuItem(
            id = Constants.ID_RATE_ON_PLAYSTORE,
            icon = R.drawable.ic_customer_review,
            title = R.string.rate_space_dawn
        ),
        NavDrawerMenuItem(
            id = Constants.ID_MORE_APPS_FROM_DEVELOPER,
            icon = R.drawable.ic_more_apps,
            title = R.string.more_apps_from_the_developer
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MdcTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SpaceDawnApp()
                }
            }
        }
    }

    @Composable
    fun SpaceDawnApp() {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            bottomBarScreens.find { it.route == currentDestination?.route } ?: ArticlesList

        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                DrawerHeader()
                DrawerBody(
                    items = navDrawerMenuList
                ) { clickedOption ->
                    val customTabIntent = CustomTabsIntent.Builder().build()
                    when (clickedOption.id) {
                        Constants.ID_VIEW_GITHUB_REPO -> {
                            try {
                                startActivity(
                                    Intent(
                                        ACTION_VIEW,
                                        Uri.parse("market://details?id=$packageName")
                                    )
                                )
                            } catch (e: ActivityNotFoundException) {
                                customTabIntent.launchUrl(
                                    this@MainActivity,
                                    Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                                )
                            }
                        }
                        Constants.ID_RATE_ON_PLAYSTORE -> {
                            customTabIntent.launchUrl(
                                this@MainActivity,
                                Uri.parse(Constants.SPACE_DAWN_PLAYSTORE_LINK)
                            )
                        }
                        Constants.ID_MORE_APPS_FROM_DEVELOPER -> {
                            customTabIntent.launchUrl(
                                this@MainActivity,
                                Uri.parse(Constants.QSOL_PLAYSTORE_LINK)
                            )
                        }
                    }
                }
            },
            bottomBar = {
                BottomNavigationBar(
                    allScreens = bottomBarScreens,
                    onTabSelected = { newScreen ->
                        navController
                            .navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = ArticlesList.route,
                modifier = Modifier
                    .background(colorResource(id = R.color.colorPrimaryDark))
            ) {
                composable(route = ArticlesList.route) {
                    ArticlesListScreen(modifier = Modifier.padding(innerPadding)) {
                        val customTabIntent = CustomTabsIntent.Builder().build()
                        customTabIntent.launchUrl(
                            this@MainActivity,
                            Uri.parse(it.url)
                        )
                    }
                }
                composable(route = SearchArticlesList.route) {
                    SearchArticleScreen(modifier = Modifier.padding(innerPadding)) {
                        val customTabIntent = CustomTabsIntent.Builder().build()
                        customTabIntent.launchUrl(
                            this@MainActivity,
                            Uri.parse(it.url)
                        )
                    }
                }
                composable(route = LaunchesList.route) {
                    LaunchesListScreen(modifier = Modifier.padding(innerPadding)) {
                        navController.navigateSingleTopTo(RemindersList.route)
                    }
                }

                composable(route = RemindersList.route) {
                    RemindersListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
