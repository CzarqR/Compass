package com.myniprojects.compass.ui.composes.compass

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.myniprojects.compass.R
import com.myniprojects.compass.ui.theme.ThemedPreview

@Composable
fun CompassDirectionPointer(
    @DrawableRes pointerIcon: Int,
    @StringRes contentDsc: Int,
    modifier: Modifier = Modifier,
    angle: Float = 0f,
)
{
    Image(
        modifier = modifier
            .padding(COMPASS_PADDING)
            .rotate(angle)
            .fillMaxSize(),
        painter = painterResource(id = pointerIcon),
        contentDescription = stringResource(id = contentDsc),
        contentScale = ContentScale.Fit,
    )
}

// region previews


// endregion

@Preview(showBackground = true)
@Composable
fun PreviewPointerArrow()
{
    ThemedPreview {
        CompassDirectionPointer(
            pointerIcon = R.drawable.ic_pointerdot,
            contentDsc = R.string.destination_direction,
            angle = 45f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPointerArrowDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        CompassDirectionPointer(
            pointerIcon = R.drawable.ic_pointerdot,
            contentDsc = R.string.destination_direction,
            angle = 45f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPointerLine()
{
    ThemedPreview {
        CompassDirectionPointer(
            pointerIcon = R.drawable.ic_line,
            contentDsc = R.string.destination_direction,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPointerLineDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        CompassDirectionPointer(
            pointerIcon = R.drawable.ic_line,
            contentDsc = R.string.destination_direction,
        )
    }
}