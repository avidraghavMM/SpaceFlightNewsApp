package com.raghav.spacedawn.ui.fragments.articleslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raghav.spacedawn.R
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem
import com.raghav.spacedawn.utils.Constants
import com.raghav.spacedawn.utils.Helpers.Companion.formatTo
import com.raghav.spacedawn.utils.Helpers.Companion.toDate

@Composable
fun ItemArticle(
    modifier: Modifier = Modifier,
    article: ArticlesResponseItem,
    cardClickListener: (ArticlesResponseItem) -> Unit = {}
) {
    Card(
        modifier = modifier.padding(16.dp).clickable {
            cardClickListener(article)
        },
        backgroundColor = colorResource(id = R.color.colorPrimaryDark),
        elevation = 8.dp
    ) {
        Column {
            ArticleImage(imageUrl = article.imageUrl)
            ArticleTitle(title = article.title)
            ArticleDescription(description = article.summary)
            ArticleFooter(source = article.newsSite, publishedAt = article.publishedAt)
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
        modifier = modifier.padding(
            start = 8.dp,
            top = 8.dp,
            end = 8.dp
        ).fillMaxWidth(),
        text = title,
        color = colorResource(id = R.color.colorWhite),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ArticleDescription(modifier: Modifier = Modifier, description: String) {
    Text(
        modifier = modifier.padding(
            start = 8.dp,
            top = 16.dp,
            end = 8.dp
        ).fillMaxWidth(),
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
            modifier = Modifier.weight(1f).fillMaxWidth().padding(start = 8.dp, top = 4.dp),
            text = source,
            color = colorResource(id = R.color.colorWhite),
            fontSize = 16.sp,
            maxLines = 1
        )
        Text(
            modifier = Modifier.weight(1f).fillMaxWidth().wrapContentWidth(Alignment.End)
                .padding(start = 8.dp, top = 4.dp),
            text = publishedAt
                .toDate(Constants.ARTICLE_DATE_INPUT_FORMAT)
                .formatTo(Constants.DATE_OUTPUT_FORMAT),
            fontSize = 16.sp,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun ItemArticlePreview() {
    Column {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.icon)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().size(50.dp)
        )
        ArticleTitle(title = "spaceX")
        ArticleDescription(description = "description")
        ArticleFooter(source = "source", publishedAt = "published date")
    }
}
