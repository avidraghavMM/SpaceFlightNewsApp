package com.raghav.spacedawn.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.themeadapter.material.MdcTheme
import com.raghav.spacedawn.R

@Composable
fun ItemLoadState(
    message: String,
    isProgressBarVisible: Boolean,
    isCtaVisible: Boolean,
    modifier: Modifier = Modifier,
    ctaListener: () -> Unit = {}
) {
    Card(
        modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        // Box composable required to centre the
        // CircularProgressIndicator specifically
        Box(contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    modifier = Modifier.weight(0.65f).padding(16.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                if (isCtaVisible) {
                    OutlinedButton(
                        modifier = Modifier.weight(0.35f).padding(16.dp).fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorPrimaryDark)),
                        onClick = { ctaListener() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.retry),
                            color = colorResource(id = R.color.colorWhite)
                        )
                    }
                }
            }
            if (isProgressBarVisible) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
fun ItemLoadStatePreview() {
    MdcTheme {
        ItemLoadState(
            stringResource(id = R.string.failed_to_connect),
            isProgressBarVisible = true,
            isCtaVisible = true
        )
    }
}
