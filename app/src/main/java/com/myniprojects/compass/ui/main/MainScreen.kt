package com.myniprojects.compass.ui.main

import android.location.Address
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myniprojects.compass.model.DestinationStatus
import com.myniprojects.compass.model.LocationStatus
import com.myniprojects.compass.model.SnackbarData
import com.myniprojects.compass.ui.composes.SelectDestinationDialog
import com.myniprojects.compass.ui.composes.compass.Compass
import com.myniprojects.compass.ui.composes.currentlocation.CurrentLocation
import com.myniprojects.compass.ui.composes.destination.DestinationSelector
import com.myniprojects.compass.ui.theme.CompassTheme
import com.myniprojects.compass.ui.theme.getGradientBrush
import com.myniprojects.compass.vm.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    requestPermissionOnClick: () -> Unit,
    mainViewModel: MainViewModel = viewModel()
)
{
    val (isDialogOpened, setIsDialogOpened) = remember { mutableStateOf(false) }
    
    val address: Address? = mainViewModel.address.collectAsState().value
    val addressLine: String? =
            if (address != null && address.maxAddressLineIndex >= 0)
                address.getAddressLine(0)
            else
                null
    
    val destinationStatus: DestinationStatus? = mainViewModel.destinationStatus.collectAsState(
        null
    ).value
    val destination: Location? = mainViewModel.destination.collectAsState().value
    val locationPermission: Boolean = mainViewModel.locationPermissionsStatus.collectAsState().value
    val gpsStatus: Boolean = mainViewModel.gpsState.collectAsState().value
    
    
    val snackbarEvent: SnackbarData? = mainViewModel.snackbarEvent.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    
    
    CompassTheme {
        Surface(color = MaterialTheme.colors.background) {
            
            /**
             * handle snackbar event
             */
            
            if (snackbarEvent != null)
            {
                mainViewModel.snackbarShown()
                
                val msg = stringResource(id = snackbarEvent.message)
                
                val actionLabel =
                        if (snackbarEvent.buttonText == null)
                            null
                        else
                            stringResource(id = snackbarEvent.buttonText)
                
                coroutineScope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = msg,
                        actionLabel = actionLabel,
                        duration = SnackbarDuration.Short
                    )
                    
                    when (result)
                    {
                        SnackbarResult.Dismissed -> Unit
                        SnackbarResult.ActionPerformed ->
                        {
                            snackbarEvent.action()
                        }
                    }
                }
            }
            
            Scaffold(
                scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
            ) {
                if (isDialogOpened)
                {
                    SelectDestinationDialog(
                        setIsDialogOpened = {
                            setIsDialogOpened(it)
                        },
                        setDestination = {
                            mainViewModel.setDestination(it)
                        },
                        startLocation = destination
                    )
                }
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(getGradientBrush(isSystemInDarkTheme())),
                ) {
                    
                    DestinationSelector(
                        setDestinationOnClick = {
                            setIsDialogOpened(true)
                        },
                        destination = destination,
                        distance = destinationStatus?.distance,
                        locationPermission = locationPermission,
                        isGpsTurnOn = gpsStatus,
                        requestLocationPermission = requestPermissionOnClick
                    )
                    
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        Compass(
                            direction = destinationStatus?.direction,
                            rotation = mainViewModel.rotation.collectAsState(0).value
                        )
                    }
                    
                    CurrentLocation(
                        locationStatus =
                        mainViewModel.locationResponse.collectAsState(
                            LocationStatus.Loading
                        ).value,
                        requestLocationPermission = {
                            requestPermissionOnClick()
                        },
                        locationPermission = locationPermission,
                        isGpsTurnOn = gpsStatus,
                        addressLine = addressLine
                    )
                }
            }
        }
    }
}


