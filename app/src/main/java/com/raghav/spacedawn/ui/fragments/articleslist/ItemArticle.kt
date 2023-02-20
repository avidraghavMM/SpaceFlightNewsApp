package com.raghav.spacedawn.ui.fragments.articleslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.raghav.spacedawn.R

@Composable
fun ItemArticle(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String,
    description: String,
    source: String,
    publishedAt: String
) {
    Card(
        modifier = modifier.padding(16.dp),
        backgroundColor = colorResource(id = R.color.colorPrimaryDark),
        elevation = 8.dp
    ) {
        Column {
            ArticleImage(imageUrl = imageUrl)
            ArticleTitle(title = title)
            ArticleDescription(description = description)
            ArticleFooter(source = source, publishedAt = publishedAt)
        }
    }
}

@Composable
fun ArticleImage(modifier: Modifier = Modifier, imageUrl: String) {
    AsyncImage(
        modifier = modifier.fillMaxWidth(),
        model = imageUrl,
        placeholder = painterResource(R.drawable.icon),
        error = painterResource(R.drawable.icon),
        contentDescription = null
    )
}

@Composable
fun ArticleTitle(modifier: Modifier = Modifier, title: String) {
    Text(
        modifier = modifier
            .padding(
                start = 8.dp,
                top = 8.dp,
                end = 8.dp
            )
            .fillMaxWidth(),
        text = title,
        color = colorResource(id = R.color.colorWhite),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ArticleDescription(modifier: Modifier = Modifier, description: String) {
    Text(
        modifier = modifier
            .padding(
                start = 8.dp,
                top = 16.dp,
                end = 8.dp
            )
            .fillMaxWidth(),
        text = description,
        color = colorResource(id = R.color.colorWhite),
        fontSize = 16.sp,
        maxLines = 3
    )
}

@Composable
fun ArticleFooter(modifier: Modifier = Modifier, source: String, publishedAt: String) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Text(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth().padding(start = 8.dp, top = 4.dp),
            text = source,
            color = colorResource(id = R.color.colorWhite),
            fontSize = 16.sp,
            maxLines = 1
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .padding(start = 8.dp, top = 4.dp),
            text = publishedAt,
            fontSize = 16.sp,
            maxLines = 1
        )
    }
}

// preview not working with AsyncImage Composable
@Preview
@Composable
fun ItemArticlePreview() {
    Column {
        AsyncImage(
            model = painterResource(id = R.drawable.icon),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        ArticleTitle(title = "spaceX")
        ArticleDescription(description = "description")
        ArticleFooter(source = "source", publishedAt = "published date")
    }
}
