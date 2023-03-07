package com.raghav.spacedawn.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.raghav.spacedawn.R
import com.raghav.spacedawn.navigation.Destination

@Composable
fun BottomNavigationBar(
    allScreens: List<Destination>,
    onTabSelected: (Destination) -> Unit,
    currentScreen: Destination,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = colorResource(
            id = R.color.colorPrimaryDark
        )
    ) {
        allScreens.forEach { screen ->
            BottomNavigationItem(
                selected = currentScreen == screen,
                onClick = { onTabSelected(screen) },
                selectedContentColor = colorResource(id = R.color.colorAccent),
                unselectedContentColor = colorResource(id = R.color.colorWhite),
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = screen.label
                        )
                        if (currentScreen == screen) {
                            Text(text = screen.label)
                        }
                    }
                }
            )
        }
    }
}
