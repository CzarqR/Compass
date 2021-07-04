package com.myniprojects.compass.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.hasPermissions(vararg permissions: String) = permissions.all { permission ->
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Float.toRotationInDegrees(): Int = (this.toInt() + 360) % 360
fun Double.toRotationInDegrees(): Int = (this.toInt() + 360) % 360

fun Activity.arePermissionNeverAskAgain(permissions: Array<String>): Boolean =
        permissions.any {
            !ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                it
            )
        }

fun makeLocation(lat: Double, long: Double, provider: String = "") = Location(provider).apply {
    longitude = long
    latitude = lat
}

fun String.isProperLatitude(): Boolean
{
    toDoubleOrNull()?.let {
        return it <= 90.0 && it >= -90.0
    } ?: return false
}

fun String.isProperLongitude(): Boolean
{
    toDoubleOrNull()?.let {
        return it <= 180.0 && it >= -180.0
    } ?: return false
}

@Composable
fun TextStyle.sizeInDp(): Dp
{
    with(LocalDensity.current) {
        return this@sizeInDp.fontSize.toDp()
    }
}

