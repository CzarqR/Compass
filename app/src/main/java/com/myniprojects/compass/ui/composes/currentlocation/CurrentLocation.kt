package com.myniprojects.compass.ui.composes.currentlocation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.myniprojects.compass.R
import com.myniprojects.compass.model.LocationStatus

@Composable
fun CurrentLocation(
    locationStatus: LocationStatus,
    requestLocationPermission: () -> Unit,
    locationPermission: Boolean,
    isGpsTurnOn: Boolean,
    addressLine: String?,
    modifier: Modifier = Modifier,
)
{
    val textStyle = MaterialTheme.typography.body1
    
    if (!locationPermission)
    {
        NoLocationPermission(
            textStyle = textStyle,
            requestLocationPermission = requestLocationPermission,
            modifier = modifier
        )
    }
    else if (!isGpsTurnOn)
    {
        NoGps(
            textStyle = textStyle,
            modifier = modifier
        )
    }
    else
    {
        Location(
            locationStatus = locationStatus,
            addressLine = addressLine,
            textStyle = textStyle,
            modifier = modifier
        )
    }
}

@Composable
fun NoLocationPermission(
    textStyle: TextStyle,
    requestLocationPermission: () -> Unit,
    modifier: Modifier = Modifier,
    
    )
{
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .clickable {
                requestLocationPermission()
            }
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = stringResource(id = R.string.allow_location_permission),
            style = textStyle
        )
    }
}

@Composable
fun NoGps(
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
)
{
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = stringResource(id = R.string.turn_on_gps),
            style = textStyle
        )
    }
}