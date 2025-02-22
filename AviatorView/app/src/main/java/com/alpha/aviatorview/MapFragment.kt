package com.alpha.aviatorview

import android.os.Bundle
import com.alpha.aviatorview.RetrofitClient
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val handler = Handler(Looper.getMainLooper())
    private val markers = mutableMapOf<String, Marker?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-29.8587, 31.0218), 5f)) // Default to Durban

        startFlightUpdates()
    }

    private fun startFlightUpdates() {
        handler.post(object : Runnable {
            override fun run() {
                fetchAndDisplayFlights()
                handler.postDelayed(this, 30000) // Refresh every 30 seconds
            }
        })
    }

    private fun fetchAndDisplayFlights() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.openSkyApi.getFlights()
                if (response.isSuccessful) {
                    val flightData = response.body()

                    Log.d("API_RESPONSE", "Received data: ${flightData?.states?.size} flights")

                    withContext(Dispatchers.Main) {
                        flightData?.states?.let { statesList ->
                            statesList.forEach { flight ->
                                if (flight is List<*>) { // Ensure it's a list
                                    val callsign = flight.getOrNull(1) as? String ?: "Unknown"
                                    val longitude = flight.getOrNull(5) as? Double ?: 0.0
                                    val latitude = flight.getOrNull(6) as? Double ?: 0.0

                                    Log.d("FLIGHT_DATA", "Callsign: $callsign, Lat: $latitude, Lon: $longitude")

                                    if (latitude != 0.0 && longitude != 0.0) {
                                        val position = LatLng(latitude, longitude)
                                        if (markers.containsKey(callsign)) {
                                            markers[callsign]?.position = position
                                        } else {
                                            val marker = mMap.addMarker(
                                                MarkerOptions()
                                                    .position(position)
                                                    .title(callsign)
                                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                            )
                                            markers[callsign] = marker
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Log.e("API_ERROR", "Response unsuccessful: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Exception: ${e.localizedMessage}")
            }
        }
    }
}
