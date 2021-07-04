package com.myniprojects.compass.repo

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.myniprojects.compass.model.LocationStatus
import com.myniprojects.compass.utils.hasPermissions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    @ApplicationContext private val context: Context
)
{
    companion object
    {
        private val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 5000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
    
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    
    private val locationCallback = object : LocationCallback()
    {
        override fun onLocationResult(locationResult: LocationResult?)
        {
            locationResult?.lastLocation?.let { lastLocation ->
                _locationFlow.value = LocationStatus.Success(location = lastLocation)
            }
        }
    }
    
    private val _locationFlow: MutableStateFlow<LocationStatus> =
            MutableStateFlow(LocationStatus.Loading)
    
    val locationFlow = _locationFlow.asStateFlow()
    
    @SuppressLint("MissingPermission")
    fun startLocationUpdates()
    {
        if (context.hasPermissions(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
        {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }
        else
        {
            throw Exception("Permission hasn't been granted")
        }
        
        
    }
    
    fun stopLocationUpdates()
    {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
    
}