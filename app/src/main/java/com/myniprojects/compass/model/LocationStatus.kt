package com.myniprojects.compass.model

import android.location.Location

sealed class LocationStatus
{
    object Loading : LocationStatus()
    data class Success(val location: Location) : LocationStatus()
}
