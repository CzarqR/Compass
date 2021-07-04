package com.myniprojects.compass.ui.main

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.myniprojects.compass.R
import com.myniprojects.compass.model.SnackbarData
import com.myniprojects.compass.utils.arePermissionNeverAskAgain
import com.myniprojects.compass.utils.hasPermissions
import com.myniprojects.compass.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    private val mainViewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        
        setContent {
            MainScreen(
                requestPermissionOnClick = ::requestLocationPermissions,
                mainViewModel = mainViewModel
            )
        }
    }
    
    // region gps on/off
    
    private lateinit var locationManager: LocationManager
    
    fun setGpsState()
    {
        val state = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        mainViewModel.setGpsState(state)
    }
    
    private val gpsSwitchStateReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context, intent: Intent?)
        {
            setGpsState()
        }
    }
    
    // endregion
    
    // region permissions
    
    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )
    
    private val multiPermissionCallback =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                
                val granted = !permissions.containsValue(false)
                mainViewModel.setLocationPermissions(granted)
                if (granted)
                {
                    mainViewModel.startLocationUpdates()
                }
                
                if (!granted && arePermissionNeverAskAgain(locationPermissions))
                {
                    mainViewModel.showSnackbar(
                        SnackbarData(
                            message = R.string.explain_permission_request,
                            buttonText = R.string.settings,
                            action = {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts(
                                        "package",
                                        packageName,
                                        null
                                    )
                                }
                                startActivity(intent)
                            }
                        )
                    )
                }
            }
    
    
    private fun requestLocationPermissions()
    {
        multiPermissionCallback.launch(
            locationPermissions
        )
    }
    
    // endregion
    
    // region lifecycle
    
    override fun onStop()
    {
        super.onStop()
        
        unregisterReceiver(gpsSwitchStateReceiver)
        
        mainViewModel.stopRotationUpdates()
        mainViewModel.stopLocationUpdates()
    }
    
    override fun onStart()
    {
        super.onStart()
        
        registerReceiver(
            gpsSwitchStateReceiver,
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        )
        setGpsState()
        mainViewModel.startRotationUpdates()
        
        val locPer = hasPermissions(*locationPermissions)
        mainViewModel.setLocationPermissions(locPer)
        if (locPer)
        {
            mainViewModel.startLocationUpdates()
        }
    }
    
    // endregion
}