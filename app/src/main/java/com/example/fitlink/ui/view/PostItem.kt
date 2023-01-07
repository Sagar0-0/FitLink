package com.example.fitlink.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.fitlink.model.FitLinkPost

@Composable
fun PostItem(
    fitLinkPost: FitLinkPost,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            val image = fitLinkPost.imageUrl
            if (image.isNotBlank()) {
                Image(
                    painter = rememberAsyncImagePainter(image),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier =Modifier.fillMaxWidth().size(200.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .wrapContentWidth(Alignment.Start)
                ) {
                    Text(
                        text = fitLinkPost.title,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    val authorName = fitLinkPost.user.name.ifBlank { "Arnold Schwarzenegger" }
                    Text(
                        text = authorName,
                        style = MaterialTheme.typography.h5,
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = fitLinkPost.category,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.End,
                    fontSize = 10.sp,
                    color = Color.Red
                )
            }
        }
    }
}