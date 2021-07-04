package com.myniprojects.compass.ui.composes.destination

import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.myniprojects.compass.R
import com.myniprojects.compass.utils.sizeInDp

@Composable
fun DestinationDistance(
    locationPermission: Boolean,
    isGpsTurnOn: Boolean,
    distance: Float?,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    requestLocationPermission: () -> Unit
)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        
        if (!locationPermission)
        {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.to_see_distance),
                    style = textStyle
                )
                
                Spacer(modifier = Modifier.width(1.dp))
                
                TextButton(onClick = {
                    requestLocationPermission()
                }) {
                    
                    Text(
                        text = stringResource(id = R.string.give_location_permission),
                        style = textStyle
                    )
                }
            }
        }
        else if (!isGpsTurnOn)
        {
            Text(
                text = stringResource(id = R.string.turn_on_gps_to_see_distance),
                style = textStyle
            )
        }
        else
        {
            Text(
                text = stringResource(
                    id = R.string.distance
                ),
                style = textStyle
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            if (distance != null)
            {
                Text(
                    text = if (distance < 10_000)
                    { // show in meters
                        stringResource(
                            id = R.string.distance_format_m,
                            distance,
                        )
                    }
                    else
                    { // show in km
                        stringResource(
                            id = R.string.distance_format_km,
                            (distance / 1000),
                        )
                    },
                    style = textStyle,
                    color = MaterialTheme.colors.primary
                )
            }
            else
            {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(textStyle.sizeInDp()),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

@Composable
fun DestinationCords(
    destination: Location,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    setDestinationOnClick: () -> Unit,
)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(
                id = R.string.destination
            ),
            style = textStyle
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = stringResource(
                id = R.string.cord_format,
                destination.latitude,
                destination.longitude
            ),
            style = textStyle,
            color = MaterialTheme.colors.primary
        )
        
        Spacer(modifier = Modifier.width(2.dp))
        
        TextButton(
            onClick = { setDestinationOnClick() },
            contentPadding = PaddingValues(0.dp)
        ) {
            
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = stringResource(id = R.string.pick_one),
                modifier = Modifier.size(textStyle.sizeInDp())
            
            )
        }
    }
}
