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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raghav.spacedawn.R
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.ui.fragments.launcheslist.CircularImage
import com.raghav.spacedawn.ui.fragments.launcheslist.Padding

@Composable
fun ItemReminder(
    reminder: ReminderModelClass,
    modifier: Modifier = Modifier,
    cancelReminderListener: (ReminderModelClass) -> Unit = {}
) {
    Card(
        modifier.padding(top = 8.dp, bottom = 8.dp),
        elevation = 8.dp,
        backgroundColor = colorResource(
            id = R.color.colorPrimaryDark
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            CircularImage(imageUrl = reminder.image, padding = Padding(start = 8.dp, top = 8.dp))
            ReminderDetails(reminder) {
                cancelReminderListener(reminder)
            }
        }
    }
}

@Composable
fun ReminderDetails(
    reminder: ReminderModelClass,
    modifier: Modifier = Modifier,
    cancelButtonClicked: (ReminderModelClass) -> Unit = {}
) {
    Column(modifier) {
        Text(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    top = 8.dp
                )
                .fillMaxWidth(),
            color = colorResource(id = R.color.colorWhite),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            text = reminder.name
        )
        Text(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    top = 8.dp
                )
                .fillMaxWidth(),
            color = colorResource(id = R.color.colorWhite),
            fontWeight = FontWeight.Bold,
            text = reminder.dateTime
        )

        OutlinedButton(
            modifier = Modifier.padding(
                start = 8.dp
            ),
            onClick = { cancelButtonClicked(reminder) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text(
                color = colorResource(id = R.color.colorWhite),
                text = stringResource(id = R.string.cancel_it)
            )
        }
    }
}
