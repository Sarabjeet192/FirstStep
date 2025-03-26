package com.cgc.firststep.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.cgc.firststep.databinding.ActivityLocationExampleScreenBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import java.util.Locale

class LocationExampleScreen : AppCompatActivity() {

    private lateinit var binding: ActivityLocationExampleScreenBinding
    private lateinit var gpsEnableLauncher: ActivityResultLauncher<IntentSenderRequest>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var addLatitude = ""
    private var addLongitude = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationExampleScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.leCheckPermission.setOnClickListener {
            checkLocationPermission()
        }

        binding.getLocation.setOnClickListener {
            val intent = Intent(this, LocPickerActivity::class.java)
            locationPickerLauncher.launch(intent)
        }

        gpsEnableLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // GPS is enabled, proceed to get the current location
                binding.leStatus.text = "GPS ENABLE"
               // getCurrentLocation()
            } else {
                // GPS not enabled, handle appropriately
                binding.leStatus.text = "GPS NOT ENABLE"
              //  Toast.makeText(this, "GPS must be enabled to use this feature", Toast.LENGTH_LONG).show()
            }
        }


    }

    private val locationPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            binding.leStatus.text = data.toString()
        }
    }

    private fun checkLocationPermission() {
        val permissionList = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permission
            locationPermissionLauncher.launch(permissionList.toTypedArray())
        } else {
            binding.leStatus.text = "Permission Granted"

            checkGPSEnabled()

        }
    }


    private fun checkGPSEnabled() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)

        // Check whether the location settings are satisfied
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            // GPS is enabled, get the current location
            binding.leStatus.text = "GPS ENABLE"
            getCurrentLocation()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    // Create an IntentSenderRequest and launch the GPS enable dialog
                    val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                    gpsEnableLauncher.launch(intentSenderRequest)

                } catch (sendEx: IntentSender.SendIntentException) {
                    sendEx.printStackTrace()
                }
            } else {

                // Unable to resolve the issue (handle appropriately)
                Toast.makeText(
                    this,
                    "Location services are required to use this feature",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    // Location permission launcher
    private val locationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val granted2 = result[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (granted || granted2) {
            binding.leStatus.text = "Permission Granted"
        } else {
            Toast.makeText(this, "Location permission required!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                addLatitude = location.latitude.toString()
                addLongitude = location.longitude.toString()

                try {
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                    addresses?.firstOrNull()?.let { address ->
                        updateUIWithAddress(address)
                    }
                } catch (e: Exception) {
                      Toast.makeText(this, "Unable to fetch address. Please try again.", Toast.LENGTH_SHORT).show()
                }
            } ?: run {

                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {

            Toast.makeText(this, "Error getting location: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUIWithAddress(address: Address) {

        val addressLine = address.getAddressLine(0) ?: "Address not available"
        binding.leStatus.text = addressLine

    }
}