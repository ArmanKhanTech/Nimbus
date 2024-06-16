package com.android.nimbus.utility

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.Locale

class LocationUtility(
    private val activity: Activity
) {
    fun getCity(): String? {
        val permission = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        return if (permission == PackageManager.PERMISSION_GRANTED) {
            val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            val location = locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            return if (location != null && Geocoder.isPresent()) {
                val geocoder = Geocoder(activity, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                addresses?.get(0)?.locality
            } else {
                "Unknown"
            }
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            "Permission not granted"
        }
    }

    companion object {
        const val REQUEST_CODE = 1234
    }
}