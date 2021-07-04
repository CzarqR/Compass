package com.myniprojects.compass.vm

import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myniprojects.compass.model.DestinationStatus
import com.myniprojects.compass.model.LocationStatus
import com.myniprojects.compass.model.SnackbarData
import com.myniprojects.compass.repo.LocationRepository
import com.myniprojects.compass.repo.RotationRepository
import com.myniprojects.compass.utils.toRotationInDegrees
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * rotation update frequency
 */
const val UPDATE_FREQUENCY = 250
const val MINIMAL_DIFFERENCE_TO_LOAD_ADDRESS = 20 // meters

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val rotationRepository: RotationRepository,
    private val geocoder: Geocoder
) : ViewModel()
{
    
    // region location / address
    
    private val _locationPermissionsStatus: MutableStateFlow<Boolean> =
            MutableStateFlow(false)
    
    val locationPermissionsStatus = _locationPermissionsStatus.asStateFlow()
    
    fun setLocationPermissions(areGranted: Boolean)
    {
        _locationPermissionsStatus.value = areGranted
    }
    
    val locationResponse = locationRepository.locationFlow.map {
        if (it is LocationStatus.Success)
        {
            loadAddress(it.location)
        }
        it
    }
    
    fun startLocationUpdates() = locationRepository.startLocationUpdates()
    
    fun stopLocationUpdates() = locationRepository.stopLocationUpdates()
    
    private var lastLoadedLocation: Location? = null
    
    private fun loadAddress(location: Location)
    {
        if (lastLoadedLocation == null || location.distanceTo(lastLoadedLocation!!) > MINIMAL_DIFFERENCE_TO_LOAD_ADDRESS)
        {
            Timber.d("Requesting address")
            lastLoadedLocation = location
            
            viewModelScope.launch(Dispatchers.IO) {
                
                _address.value = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                ).getOrNull(0)
            }
        }
    }
    
    private val _address: MutableStateFlow<Address?> = MutableStateFlow(null)
    val address = _address.asStateFlow()
    
    // endregion
    
    // region rotation
    
    private var lastRotationUpdateTime = 0L
    private var lastSavedRotation = 0
    
    /**
     * rotation is changing to fast and very irregularly
     * it jumps in range of 10 degrees
     * average doesn't help
     */
    val rotation: Flow<Int> = rotationRepository.rotationFlow.map { r ->
        val c = System.currentTimeMillis()
        
        if (lastRotationUpdateTime + UPDATE_FREQUENCY < c)
        {
            lastRotationUpdateTime = c
            lastSavedRotation = r
        }
        
        lastSavedRotation
    }
    
    
    fun startRotationUpdates() = rotationRepository.startObservingRotation()
    fun stopRotationUpdates() = rotationRepository.stopObservingRotation()
    
    // endregion
    
    // region destination
    
    fun setDestination(location: Location?)
    {
        _destination.value = location
    }
    
    private val _destination: MutableStateFlow<Location?> =
            MutableStateFlow(null)
    
    val destination = _destination.asStateFlow()
    
    val destinationStatus: Flow<DestinationStatus?> = _destination.combine(locationResponse) { dest, loc ->
        
        if (dest != null && loc is LocationStatus.Success)
        {
            DestinationStatus(
                distance = loc.location.distanceTo(dest),
                direction = loc.location.bearingTo(dest).toRotationInDegrees()
            )
        }
        else
        {
            null
        }
    }
    
    // endregion
    
    // region snackbar event
    
    private val _snackbarEvent: MutableStateFlow<SnackbarData?> =
            MutableStateFlow(null)
    
    val snackbarEvent = _snackbarEvent.asStateFlow()
    
    fun showSnackbar(snackbarData: SnackbarData)
    {
        _snackbarEvent.value = snackbarData
    }
    
    fun snackbarShown()
    {
        _snackbarEvent.value = null
    }
    
    // endregion
    
    // region GPS state
    
    private val _gpsState: MutableStateFlow<Boolean> =
            MutableStateFlow(false)
    
    val gpsState = _gpsState.asStateFlow()
    
    fun setGpsState(state: Boolean)
    {
        _gpsState.value = state
    }
    
    // endregion
    
}
