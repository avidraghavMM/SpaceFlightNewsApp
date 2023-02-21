package com.raghav.spacedawn.ui.fragments.reminderlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.themeadapter.material.MdcTheme
import com.raghav.spacedawn.R
import com.raghav.spacedawn.ui.fragments.launcheslist.CircularImage
import com.raghav.spacedawn.ui.fragments.launcheslist.Padding

@Composable
fun ItemReminder(
    imageUrl: String?,
    title: String,
    launchDate: String,
    modifier: Modifier = Modifier,
    ctaListener: () -> Unit = {}
) {
    Card(
        modifier.padding(top = 8.dp, bottom = 8.dp),
        elevation = 8.dp,
        backgroundColor = colorResource(
            id = R.color.colorPrimaryDark
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            CircularImage(imageUrl = imageUrl, padding = Padding(start = 8.dp, top = 8.dp))
            ReminderDetails(title, launchDate) {
                ctaListener()
            }
        }
    }
}

@Composable
fun ReminderDetails(
    title: String,
    launchDate: String,
    modifier: Modifier = Modifier,
    cancelButtonClicked: () -> Unit = {}
) {
    Column(modifier) {
        Text(
            modifier = Modifier.padding(
                start = 8.dp,
                top = 8.dp
            ).fillMaxWidth(),
            color = colorResource(id = R.color.colorWhite),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            text = title
        )
        Text(
            modifier = Modifier.padding(
                start = 8.dp,
                top = 8.dp
            ).fillMaxWidth(),
            color = colorResource(id = R.color.colorWhite),
            fontWeight = FontWeight.Bold,
            text = launchDate
        )

        OutlinedButton(
            modifier = Modifier.padding(
                start = 8.dp
            ),
            onClick = { cancelButtonClicked() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text(
                color = colorResource(id = R.color.colorWhite),
                text = stringResource(id = R.string.cancel_it)
            )
        }
    }
}

@Preview
@Composable
fun ItemReminderPreview() {
    MdcTheme {
        ItemReminder(imageUrl = null, title = "title", launchDate = "launch date")
    }
}
