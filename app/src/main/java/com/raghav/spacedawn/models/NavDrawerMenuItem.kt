package com.raghav.spacedawn.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class NavDrawerMenuItem(
    val id: String,
    @DrawableRes
    val icon: Int,
    @StringRes
    val title: Int
)
