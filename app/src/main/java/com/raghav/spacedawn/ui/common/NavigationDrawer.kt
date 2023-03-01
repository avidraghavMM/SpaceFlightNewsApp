package com.raghav.spacedawn.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raghav.spacedawn.R
import com.raghav.spacedawn.models.NavDrawerMenuItem
import com.raghav.spacedawn.ui.spacing

@Composable
fun DrawerHeader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth()
            .background(colorResource(id = R.color.colorCream)),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().padding(bottom = spacing.large)
            )
        }
    }
}

@Composable
fun DrawerBody(
    items: List<NavDrawerMenuItem>,
    modifier: Modifier = Modifier,
    onItemClick: (NavDrawerMenuItem) -> Unit
) {
    Column(
        modifier.fillMaxHeight()
            .background(colorResource(id = R.color.colorPrimaryDark))
            .padding(top = spacing.large),
        verticalArrangement = Arrangement.spacedBy(spacing.large)
    ) {
        items.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item) }
            ) {
                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    modifier = Modifier.padding(
                        start = spacing.medium,
                        end = spacing.medium
                    ).size(32.dp)
                )
                Text(
                    text = stringResource(id = item.title),
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.colorWhite)
                )
            }
        }
    }
}
