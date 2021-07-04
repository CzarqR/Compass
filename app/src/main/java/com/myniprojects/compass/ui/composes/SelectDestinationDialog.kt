package com.myniprojects.compass.ui.composes

import android.location.Location
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myniprojects.compass.R
import com.myniprojects.compass.ui.theme.ThemedPreview
import com.myniprojects.compass.utils.isProperLatitude
import com.myniprojects.compass.utils.isProperLongitude
import com.myniprojects.compass.utils.makeLocation

@Composable
fun SelectDestinationDialog(
    setIsDialogOpened: (Boolean) -> Unit,
    setDestination: (Location?) -> Unit,
    startLocation: Location?
)
{
    var latText by rememberSaveable { mutableStateOf(startLocation?.latitude?.toString() ?: "") }
    var longText by rememberSaveable { mutableStateOf(startLocation?.longitude?.toString() ?: "") }
    
    val latIsError = !latText.isProperLatitude()
    val longIsError = !longText.isProperLongitude()
    
    AlertDialog(
        onDismissRequest = { setIsDialogOpened(false) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.select_destination),
                    style = MaterialTheme.typography.h6
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                CordTextField(
                    value = latText,
                    onValueChange = {
                        latText = it
                    },
                    label = R.string.latitude,
                    isValueError = latIsError,
                    icon = R.drawable.ic_latitude
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                CordTextField(
                    value = longText,
                    onValueChange = {
                        longText = it
                    },
                    label = R.string.longitude,
                    isValueError = longIsError,
                    icon = R.drawable.ic_longitude
                )
                
                if (startLocation != null)
                {
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    TextButton(onClick = {
                        setDestination(null)
                        setIsDialogOpened(false)
                    }) {
                        
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = stringResource(id = R.string.remove_destination),
                            tint = MaterialTheme.colors.error
                        )
                        
                        Spacer(modifier = Modifier.width(4.dp))
                        
                        Text(
                            text = stringResource(id = R.string.remove_destination),
                            style = MaterialTheme.typography.h6.copy(fontSize = 18.sp),
                            color = MaterialTheme.colors.error
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (!latIsError && !longIsError)
                {
                    setDestination(
                        makeLocation(latText.toDouble(), longText.toDouble())
                    )
                    setIsDialogOpened(false)
                }
                
            }) {
                Text(stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                setIsDialogOpened(false)
            }) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}

@Composable
fun CordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    isValueError: Boolean,
    maxLength: Int = 11,
    @DrawableRes icon: Int
)
{
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.length < maxLength)
                onValueChange(it)
        },
        label = { Text(stringResource(id = label)) },
        isError = isValueError,
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        leadingIcon = {
            Icon(painter = painterResource(id = icon), contentDescription = null)
        }
    )
}


// region previews [Only visible after deploying]

@Preview(showBackground = true)
@Composable
fun SelectDestinationDialogDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        SelectDestinationDialog(
            setIsDialogOpened = {},
            setDestination = {},
            startLocation = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectDestinationDialog()
{
    ThemedPreview {
        SelectDestinationDialog(
            setIsDialogOpened = {},
            setDestination = {},
            startLocation = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectDestinationDialogWithStartLocationDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        SelectDestinationDialog(
            setIsDialogOpened = {},
            setDestination = {},
            startLocation = makeLocation(14.151, 12.124)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectDestinationDialogWithStartLocation()
{
    ThemedPreview {
        SelectDestinationDialog(
            setIsDialogOpened = {},
            setDestination = {},
            startLocation = makeLocation(14.151, 12.124)
        )
    }
}

// endregion