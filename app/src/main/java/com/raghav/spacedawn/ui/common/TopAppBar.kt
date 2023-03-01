package com.raghav.spacedawn.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.raghav.spacedawn.R

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    onHamburgerIconClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        backgroundColor = colorResource(id = R.color.colorPrimaryDark),
        contentColor = colorResource(id = R.color.colorWhite),
        navigationIcon = {
            IconButton(onClick = onHamburgerIconClick) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        },
        modifier = modifier
    )
}
