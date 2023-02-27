package com.raghav.spacedawn.ui.fragments.launcheslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raghav.spacedawn.R
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import com.raghav.spacedawn.utils.Constants
import com.raghav.spacedawn.utils.Helpers.Companion.formatTo
import com.raghav.spacedawn.utils.Helpers.Companion.toDate

@Composable
fun ItemLaunch(
    modifier: Modifier = Modifier,
    launch: LaunchLibraryResponseItem,
    onAdReminderClicked: (LaunchLibraryResponseItem) -> Unit = {}
) {
    Row(modifier) {
        CircularImage(imageUrl = launch.image, padding = Padding(start = 8.dp, top = 40.dp))
        LaunchDetails(
            launch = launch
        ) {
            onAdReminderClicked(it)
        }
    }
}

@Composable
fun LaunchDetails(
    modifier: Modifier = Modifier,
    launch: LaunchLibraryResponseItem,
    onAdReminderClicked: (LaunchLibraryResponseItem) -> Unit
) {
    Column(modifier) {
        Text(
            modifier = Modifier.padding(
                start = 8.dp,
                top = 8.dp
            ),
            color = colorResource(id = R.color.colorWhite),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            text = launch.name
        )
        Text(
            modifier = Modifier.padding(
                start = 8.dp
            ),
            color = colorResource(id = R.color.colorWhite),
            text = launch.launch_service_provider.name
        )
        Text(
            modifier = Modifier.padding(
                start = 8.dp,
                top = 8.dp
            ),
            fontSize = 16.sp,
            color = colorResource(id = R.color.colorWhite),
            text = stringResource(id = R.string.rocket, launch.rocket.configuration.full_name)
        )

        Row {
            Text(
                modifier = Modifier.padding(
                    start = 8.dp,
                    top = 8.dp
                ),
                fontSize = 16.sp,
                color = colorResource(id = R.color.colorWhite),
                text = stringResource(id = R.string.Status)
            )
            Text(
                modifier = Modifier.padding(
                    start = 8.dp,
                    top = 8.dp
                ),
                fontSize = 16.sp,
                color = when (launch.status.name) {
                    "To Be Determined" -> Color.Red
                    "Go for Launch" -> Color.Green
                    "To Be Confirmed" -> Color.Yellow
                    else -> Color.White
                },
                text = launch.status.name
            )
        }

        Text(
            modifier = Modifier.padding(
                start = 8.dp,
                top = 8.dp
            ),
            color = colorResource(id = R.color.colorWhite),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            text = launch.net.toDate(Constants.LAUNCH_DATE_INPUT_FORMAT).formatTo(
                Constants.DATE_OUTPUT_FORMAT
            )
        )

        OutlinedButton(
            modifier = Modifier.padding(
                start = 8.dp
            ),
            onClick = { onAdReminderClicked(launch) },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorAccent))
        ) {
            Text(
                color = colorResource(id = R.color.colorWhite),
                text = stringResource(id = R.string.add_reminder)
            )
        }
    }
}

@Composable
fun CircularImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    padding: Padding = Padding()
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl).error(R.drawable.icon)
            .placeholder(R.drawable.icon).build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.padding(
            start = padding.start,
            top = padding.top,
            end = padding.end,
            bottom = padding.bottom
        ).size(80.dp).clip(CircleShape)
    )
}

data class Padding(
    val top: Dp = 0.dp,
    val bottom: Dp = 0.dp,
    val start: Dp = 0.dp,
    val end: Dp = 0.dp
)
