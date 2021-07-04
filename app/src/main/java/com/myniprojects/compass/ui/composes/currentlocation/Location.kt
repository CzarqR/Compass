package com.myniprojects.compass.ui.composes.currentlocation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myniprojects.compass.R
import com.myniprojects.compass.model.LocationStatus
import com.myniprojects.compass.ui.theme.ThemedPreview
import com.myniprojects.compass.utils.makeLocation

@Composable
fun Location(
    locationStatus: LocationStatus,
    addressLine: String?,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
)
{
    when (locationStatus)
    {
        LocationStatus.Loading ->
        {
            Card(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(20.dp),
                    strokeWidth = 2.dp
                )
            }
        }
        is LocationStatus.Success ->
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = modifier
                        .padding(8.dp, 12.dp, 8.dp, 12.dp)
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = stringResource(
                            id = R.string.cord_format,
                            locationStatus.location.latitude,
                            locationStatus.location.longitude
                        ),
                        color = MaterialTheme.colors.primary,
                        style = textStyle
                    )
                }
                addressLine?.let {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 4.dp),
                        text = addressLine,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}


// region previews

@Preview(showBackground = true)
@Composable
fun PreviewLocationLoading()
{
    ThemedPreview {
        Location(LocationStatus.Loading, null, MaterialTheme.typography.body1)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLocationLoadingDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        Location(LocationStatus.Loading, null, MaterialTheme.typography.body1)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLocationSuccess()
{
    ThemedPreview {
        Location(
            LocationStatus.Success(makeLocation(12.12349, 84.124129)),
            "Road 30, 44-100 Warsaw, Poland",
            MaterialTheme.typography.body1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLocationSuccessDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        Location(
            LocationStatus.Success(makeLocation(12.12349, 84.124129)),
            "Road 30, 44-100 Warsaw, Poland",
            MaterialTheme.typography.body1
        )
    }
}


// endregion