package com.raghav.spacedawn.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val small: Dp,
    val normal: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp
)

data class Height(
    val tabHeight: Dp
)

val LocalSpacing = compositionLocalOf {
    Spacing(small = 2.dp, normal = 4.dp, medium = 8.dp, large = 16.dp, extraLarge = 32.dp)
}

val spacing
    @ReadOnlyComposable
    @Composable
    get() = LocalSpacing.current

val LocalHeight = compositionLocalOf {
    Height(tabHeight = 56.dp)
}

val height
    @ReadOnlyComposable
    @Composable
    get() = LocalHeight.current
