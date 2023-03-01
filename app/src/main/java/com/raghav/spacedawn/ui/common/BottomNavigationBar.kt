package com.raghav.spacedawn.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import com.raghav.spacedawn.R
import com.raghav.spacedawn.navigation.Destination
import com.raghav.spacedawn.ui.height
import com.raghav.spacedawn.ui.spacing

@Composable
fun BottomNavigationBar(
    allScreens: List<Destination>,
    onTabSelected: (Destination) -> Unit,
    currentScreen: Destination,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(height.tabHeight).fillMaxWidth(),
        color = colorResource(id = R.color.colorPrimaryDark)
    ) {
        Row(
            Modifier
                .selectableGroup()
                .padding(start = spacing.large, end = spacing.large),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            allScreens.forEach { screen ->
                SelectableTab(
                    text = screen.label,
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
fun SelectableTab(
    text: String,
    @DrawableRes icon: Int,
    onSelected: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) colorResource(id = R.color.colorAccent)
        else colorResource(id = R.color.colorWhite)
    )
    Column(
        modifier
            .animateContentSize()
            .height(height.tabHeight)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                ),
                onClick = onSelected,
                role = Role.Tab
            ).semantics {
                this.selected = selected
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = text,
                tint = tabTintColor
            )
        }

        if (selected) {
            Text(text = text, color = tabTintColor)
        }
    }
}
