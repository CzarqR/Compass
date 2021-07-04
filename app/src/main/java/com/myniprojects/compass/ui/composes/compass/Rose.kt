package com.myniprojects.compass.ui.composes.compass

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.myniprojects.compass.R
import com.myniprojects.compass.ui.theme.ThemedPreview

@Composable
fun Rose(
    angle: Float,
    rotation: Int,
    modifier: Modifier = Modifier
)
{
    Image(
        modifier = modifier
            .padding(COMPASS_PADDING)
            .fillMaxSize()
            .rotate(angle),
        painter = painterResource(id = R.drawable.ic_rose),
        contentDescription = stringResource(id = R.string.compass),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(
            color = MaterialTheme.colors.onBackground
        )
    )
    
    Text(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        text = stringResource(id = R.string.degree_format, rotation),
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.h5
    )
}


// region previews

@Preview(showBackground = true)
@Composable
fun PreviewRoss()
{
    ThemedPreview {
        Rose(angle = 30f, rotation = -45)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRossDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        Rose(angle = 30f, rotation = -45)
    }
}


// endregion