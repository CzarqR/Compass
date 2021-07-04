package com.myniprojects.compass.ui.composes.destination

import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WhereToVote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myniprojects.compass.R
import com.myniprojects.compass.ui.theme.ThemedPreview
import com.myniprojects.compass.utils.makeLocation
import com.myniprojects.compass.utils.sizeInDp


@Composable
fun DestinationSelector(
    setDestinationOnClick: () -> Unit,
    destination: Location?,
    distance: Float?,
    locationPermission: Boolean,
    isGpsTurnOn: Boolean,
    requestLocationPermission: () -> Unit,
    modifier: Modifier = Modifier,
)
{
    val textStyle = MaterialTheme.typography.h6.copy(fontSize = 18.sp)
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        horizontalAlignment = if (destination == null) Alignment.CenterHorizontally else Alignment.Start
    ) {
        if (destination == null)
        {
            NoDestination(
                textStyle = textStyle,
                setDestinationOnClick = setDestinationOnClick
            )
        }
        else
        {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                
                DestinationCords(
                    destination = destination,
                    textStyle = textStyle,
                    setDestinationOnClick = setDestinationOnClick
                )
                
                DestinationDistance(
                    locationPermission = locationPermission,
                    isGpsTurnOn = isGpsTurnOn,
                    distance = distance,
                    textStyle = textStyle,
                    requestLocationPermission = requestLocationPermission
                )
            }
        }
    }
}


@Composable
fun NoDestination(
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    setDestinationOnClick: () -> Unit,
)
{
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.destination_isnt_selected),
            style = textStyle
        )
        
        Spacer(modifier = Modifier.width(1.dp))
        
        TextButton(onClick = { setDestinationOnClick() }) {
            
            Icon(
                imageVector = Icons.Outlined.WhereToVote,
                contentDescription = stringResource(id = R.string.pick_one),
                modifier = Modifier
                    .size(textStyle.sizeInDp())
            )
            
            Spacer(modifier = Modifier.width(2.dp))
            
            
            Text(
                text = stringResource(id = R.string.pick_one),
                style = textStyle
            )
        }
    }
}

// region previews

@Preview(showBackground = true, widthDp = 450)
@Composable
fun DestinationSelectorNoDestinationDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        DestinationSelector(
            setDestinationOnClick = { },
            destination = null,
            distance = null,
            locationPermission = true,
            isGpsTurnOn = true,
            requestLocationPermission = {}
        )
    }
}


@Preview(showBackground = true, widthDp = 450)
@Composable
fun DestinationSelectorNoDestination()
{
    ThemedPreview {
        DestinationSelector(
            setDestinationOnClick = { },
            destination = null,
            distance = null,
            locationPermission = true,
            isGpsTurnOn = true,
            requestLocationPermission = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 450)
@Composable
fun DestinationSelectorWithDestinationMDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        DestinationSelector(
            setDestinationOnClick = { },
            destination = makeLocation(51.14121, 52.12412),
            distance = 1244.131f,
            locationPermission = true,
            isGpsTurnOn = true,
            requestLocationPermission = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 450)
@Composable
fun DestinationSelectorWithDestinationM()
{
    ThemedPreview {
        DestinationSelector(
            setDestinationOnClick = { },
            destination = makeLocation(51.14121, 52.12412),
            distance = 1244.131f,
            locationPermission = true,
            isGpsTurnOn = true,
            requestLocationPermission = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 450)
@Composable
fun DestinationSelectorWithDestinationKmDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        DestinationSelector(
            setDestinationOnClick = { },
            destination = makeLocation(51.14121, 52.12412),
            distance = 12424.124f,
            locationPermission = true,
            isGpsTurnOn = true,
            requestLocationPermission = {}
        )
    }
}


@Preview(showBackground = true, widthDp = 450)
@Composable
fun DestinationSelectorWithDestinationKm()
{
    ThemedPreview {
        DestinationSelector(
            setDestinationOnClick = { },
            destination = makeLocation(51.14121, 52.12412),
            distance = 12424.124f,
            locationPermission = true,
            isGpsTurnOn = true,
            requestLocationPermission = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 450)
@Composable
fun DestinationSelectorNoLocPermDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        DestinationSelector(
            setDestinationOnClick = { },
            destination = makeLocation(51.14121, 52.12412),
            distance = 12424.124f,
            locationPermission = false,
            isGpsTurnOn = true,
            requestLocationPermission = {}
        )
    }
}


@Preview(showBackground = true, widthDp = 450)
@Composable
fun DestinationSelectorNoLocPerm()
{
    ThemedPreview {
        DestinationSelector(
            setDestinationOnClick = { },
            destination = makeLocation(51.14121, 52.12412),
            distance = 12424.124f,
            locationPermission = false,
            isGpsTurnOn = true,
            requestLocationPermission = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 450)
@Composable
fun DestinationSelectorNoGpsDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        DestinationSelector(
            setDestinationOnClick = { },
            destination = makeLocation(51.14121, 52.12412),
            distance = 12424.124f,
            locationPermission = true,
            isGpsTurnOn = false,
            requestLocationPermission = {}
        )
    }
}


@Preview(showBackground = true, widthDp = 450)
@Composable
fun DestinationSelectorNoGps()
{
    ThemedPreview {
        DestinationSelector(
            setDestinationOnClick = { },
            destination = makeLocation(51.14121, 52.12412),
            distance = 12424.124f,
            locationPermission = true,
            isGpsTurnOn = false,
            requestLocationPermission = {}
        )
    }
}

// endregion


