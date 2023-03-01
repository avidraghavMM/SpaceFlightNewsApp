package com.raghav.spacedawn.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.themeadapter.material.MdcTheme
import com.raghav.spacedawn.R
import com.raghav.spacedawn.navigation.*
import com.raghav.spacedawn.ui.common.BottomNavigationBar
import com.raghav.spacedawn.ui.fragments.articleslist.ArticlesListScreen
import com.raghav.spacedawn.ui.fragments.launcheslist.LaunchesListScreen
import com.raghav.spacedawn.ui.fragments.reminderlist.RemindersListScreen
import com.raghav.spacedawn.ui.fragments.searcharticles.SearchArticleScreen
import com.raghav.spacedawn.utils.Helpers.Companion.navigateSingleTopTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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

        Scaffold(
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
                modifier = Modifier.padding(innerPadding)
                    .background(colorResource(id = R.color.colorPrimaryDark))
            ) {
                composable(route = ArticlesList.route) {
                    ArticlesListScreen {
                        val customTabIntent = CustomTabsIntent.Builder().build()
                        customTabIntent.launchUrl(
                            this@MainActivity,
                            Uri.parse(it.url)
                        )
                    }
                }
                composable(route = SearchArticlesList.route) {
                    SearchArticleScreen {
                        val customTabIntent = CustomTabsIntent.Builder().build()
                        customTabIntent.launchUrl(
                            this@MainActivity,
                            Uri.parse(it.url)
                        )
                    }
                }
                composable(route = LaunchesList.route) {
                    LaunchesListScreen {
                        navController.navigateSingleTopTo(RemindersList.route)
                    }
                }

                composable(route = RemindersList.route) {
                    RemindersListScreen()
                }
            }
        }
    }
}
