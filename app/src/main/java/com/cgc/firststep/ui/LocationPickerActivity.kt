package com.cgc.firststep.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.cgc.firststep.R
import com.cgc.firststep.databinding.ActivityLocationPickerBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.io.IOException
import java.util.Locale

class LocPickerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityLocationPickerBinding
    private lateinit var map: GoogleMap
    private lateinit var marker: Marker
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var selectedLatLng: LatLng? = null
    private var mAddressOne = ""
    private var mAddressTwo = ""
    private var mCity = ""
    private var mPin = ""
    private var mState = ""
    private var locName = ""
    private lateinit var gpsEnableLauncher: ActivityResultLauncher<IntentSenderRequest>
    private lateinit var autocompleteLauncher: ActivityResultLauncher<Intent>
    private var locReq = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.mapKey))
        }

        checkGPSEnabled()

        // Initialize FusedLocationProviderClient to get current location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Get the SupportMapFragment and request the map to load asynchronously
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Confirm the selected location and return it back to the previous activity
        binding.confirmButton.setOnClickListener {
            selectedLatLng?.let { latLng ->
                val resultIntent = Intent().apply {
                    putExtra("addOne", mAddressOne)
                    putExtra("addTwo", mAddressTwo)
                    putExtra("addCity", mCity)
                    putExtra("addState", mState)
                    putExtra("addPin", mPin)
                    putExtra("latitude", latLng.latitude)
                    putExtra("longitude", latLng.longitude)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        gpsEnableLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // GPS is enabled, proceed to get the current location
                getCurrentLocation()
            } else {
                // GPS not enabled, handle appropriately
                Toast.makeText(this, "GPS must be enabled to use this feature", Toast.LENGTH_LONG).show()
            }
        }

        binding.searchText.setOnClickListener {
            openAutocomplete()
        }

        // Initialize the Activity Result Launcher
        autocompleteLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val place = Autocomplete.getPlaceFromIntent(intent)
                    selectedLatLng = place.latLng
                    locName = place.displayName ?:  ""

                    if (selectedLatLng != null) {
                        // Update the map and marker
                        setMarkerAtLocation(selectedLatLng!!)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng!!, 15f))
                        getAddressFromLatLng(selectedLatLng!!)
                    }
                }
            } else if (result.resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(result.data!!)
                Toast.makeText(this, "Error: ${status.statusMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openAutocomplete() {
        // Define the fields you want to retrieve
        val fields = listOf(
            Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS
        )

        // Create the intent for Autocomplete
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY, fields
        ).build(this)

        // Launch the Autocomplete activity
        autocompleteLauncher.launch(intent)
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

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Check for location permission and get the current location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            requestLocationPermission()
        }

        // Add a marker when the user clicks on the map
        map.setOnMapClickListener { latLng ->
            marker.position = latLng
            selectedLatLng = latLng
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng))

            getAddressFromLatLng(latLng)
        }
    }

    private fun getAddressFromLatLng(latLng: LatLng) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses!!.isNotEmpty()) {
                val address: Address = addresses[0]

                // Check if thoroughfare (street name) and subThoroughfare (house number) are available
//                mAddressOne = "${address.featureName ?: ""} ${address.subLocality ?: ""}" // Use featureName as a fallback (e.g., building name)
//
//                 if (address.subThoroughfare.isNullOrEmpty()) {
//
//                    if(address.maxAddressLineIndex > 0 ) {
//                       mAddressTwo = address.getAddressLine(0)
//                    }
//                    // Optionally leave empty if no house number is available
//                } else {
//                     mAddressTwo =  address.subThoroughfare  // Use house number if available
//                }

                // Populate other fields
                mCity = address.locality ?: ""
                mPin = address.postalCode ?: ""
                mState = address.adminArea ?: ""

                // Display full address using a fallback if necessary
                val addressLine = address.getAddressLine(0) ?: "Address not available"
                if(locName.isEmpty()) {
                    binding.addressText.text = addressLine
                    mAddressOne = addressLine
                }else{
                    binding.addressText.text = "$locName, $addressLine"
                    mAddressOne = "$locName, $addressLine"
                }
            } else {
                binding.addressText.text = "N/A"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            if(locReq < 20) {
                locReq++
                getCurrentLocation()
            }
            // Toast.makeText(this, "Unable to fetch address", Toast.LENGTH_LONG).show()
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                selectedLatLng = LatLng(location.latitude, location.longitude)
                setMarkerAtLocation(selectedLatLng!!)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng!!, 15f))
                getAddressFromLatLng(selectedLatLng!!)
            } else {
                if(locReq < 20) {
                    locReq++
                    getCurrentLocation()
                }
                // Handle case where location is null (e.g., location services are off)
                //Toast.makeText(this, "Unable to fetch current location", Toast.LENGTH_SHORT).show()
                // Set a default fallback location (e.g., New York)
//                selectedLatLng = LatLng(16.314209, 80.435028)
//                setMarkerAtLocation(selectedLatLng!!)
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng!!, 12f))
//                getAddressFromLatLng(selectedLatLng!!)
            }
        }
    }

    private fun setMarkerAtLocation(latLng: LatLng) {
        if (::marker.isInitialized) {
            marker.remove()
        }
        marker = map.addMarker(
            MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title("Tap to select location")
        )!!
        selectedLatLng = latLng
    }

    private fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true -> {
                    getCurrentLocation() // Permission granted
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
                    getCurrentLocation() // Permission granted (coarse location)
                }
                else -> {
                    // Permission denied
                    // Toast.makeText(this, "Location permission is required to select current location", Toast.LENGTH_LONG).show()
                    //  val defaultLocation = LatLng(40.7128, -74.0060) // Fallback default location
                    // setMarkerAtLocation(defaultLocation)
                    // map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}
