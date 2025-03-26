package com.cgc.firststep.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.cgc.firststep.R
import com.cgc.firststep.databinding.ActivityMapDirectionExampleBinding
import com.cgc.firststep.model.DirectionsResponse
import com.cgc.firststep.model.decodePolyline
import com.cgc.firststep.network.DirectionsAPI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MapDirectionExample : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapDirectionExampleBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val destination = LatLng(30.6809077, 76.6057661)  //CGC MOHALI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapDirectionExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        getUserLocation()
    }

    private fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val userLocation = LatLng(it.latitude, it.longitude)
                googleMap.addMarker(MarkerOptions().position(userLocation).title("Your Location"))
                googleMap.addMarker(MarkerOptions().position(destination).title("Destination"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12f))

                getRoute(userLocation, destination)
            }
        }
    }

    private fun getRoute(origin: LatLng, destination: LatLng) {
        val apiKey = getString(R.string.mapKey)
        val url = "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${destination.latitude},${destination.longitude}&key=$apiKey"

        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(DirectionsAPI::class.java)
        api.getDirections(url).enqueue(object : Callback<DirectionsResponse> {
            override fun onResponse(call: Call<DirectionsResponse>, response: Response<DirectionsResponse>) {
                response.body()?.routes?.firstOrNull()?.let { route ->
                    val points = route.overview_polyline.points.decodePolyline()
                    drawPolyline(points)
                }
            }
            override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun drawPolyline(points: List<LatLng>) {
        val polylineOptions = PolylineOptions().addAll(points).width(12f).color(0xFF6200EE.toInt())
        googleMap.addPolyline(polylineOptions)
    }


}
