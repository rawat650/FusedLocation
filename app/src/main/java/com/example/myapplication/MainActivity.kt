package com.example.myapplication

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() {
    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap
    var currentLocation: Location? = null

    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()
        /*   mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
           mapFragment.getMapAsync(OnMapReadyCallback{
               googleMap = it*/

        /*  googleMap.isMyLocationEnabled=true*/

        /* AIzaSyCp3h3MhleoIOx57JOwaq4nqXfhZ2oJsRA*/
/*
            val location1 = LatLng(22.9734, 78.6569)
            googleMap.addMarker(MarkerOptions().position(location1).title("My Location"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,5f))

            val location2 = LatLng(23.2599, 77.4126)
            googleMap.addMarker(MarkerOptions().position(location2).title("Bhopal"))

            val location3 = LatLng(20.5579, 74.5089)
            googleMap.addMarker(MarkerOptions().position(location3).title("Malegaon"))*/


        /*})*/
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            return
        }
        val task = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                val supportMapFragment =
                    (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)
                supportMapFragment!!.getMapAsync({
                    googleMap = it
                    val lating = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                    val markerOptions = MarkerOptions().position(lating).title("I Am Here!")
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(lating))
                    /*  googleMap.addMarker(MarkerOptions().position(lating).title("My Location"))*/
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lating,15f))
                    googleMap.addMarker(markerOptions)


                })
                }

            }

        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLocation()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}


