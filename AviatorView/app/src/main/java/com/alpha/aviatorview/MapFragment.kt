package com.alpha.aviatorview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.maps.android.SphericalUtil

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val handler = Handler(Looper.getMainLooper())
    private val markers = mutableMapOf<String, Marker?>()
    private val flightDetailsMap = mutableMapOf<String, FlightData>()

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

        // Handle marker clicks to show flight details
        mMap.setOnMarkerClickListener { marker ->
            val callsign = marker.title
            val flightData = flightDetailsMap[callsign]

            if (flightData != null) {
                showFlightDetails(flightData) // Open bottom sheet
                trackFlight(marker) // Start live tracking
            }
            true
        }
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
                    val flightDataList: List<FlightData> = response.body()?.states?.mapNotNull { flight ->
                        // Explicitly cast the flight object to List<Any?>
                        if (flight is List<*>) {
                            val callsign = flight.getOrNull(1) as? String ?: "Unknown"
                            val longitude = (flight.getOrNull(5) as? Number)?.toDouble() ?: 0.0
                            val latitude = (flight.getOrNull(6) as? Number)?.toDouble() ?: 0.0
                            val altitude = (flight.getOrNull(7) as? Number)?.toDouble() ?: 0.0
                            val speed = (flight.getOrNull(9) as? Number)?.toDouble() ?: 0.0
                            val aircraft = "Unknown"

                            if (latitude != 0.0 && longitude != 0.0) {
                                FlightData(
                                    callsign, "Unknown", "Unknown",
                                    altitude, speed, aircraft,
                                    LatLng(latitude, longitude)
                                )
                            } else null
                        } else null
                    } ?: emptyList()

                    withContext(Dispatchers.Main) {
                        updateFlightsOnMap(flightDataList)
                    }
                } else {
                    Log.e("API_ERROR", "Response unsuccessful: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Exception: ${e.localizedMessage}")
            }
        }
    }

    private fun updateFlightsOnMap(flightDataList: List<FlightData>) {
        val airplaneIcon = BitmapDescriptorFactory.fromResource(R.drawable.picon)

        flightDataList.forEach { flight ->
            val position = flight.position
            flightDetailsMap[flight.callsign] = flight

            if (markers.containsKey(flight.callsign)) {
                animateMarker(markers[flight.callsign], position)
            } else {
                val marker = mMap.addMarker(
                    MarkerOptions()
                        .position(position)
                        .title(flight.callsign)
                        .icon(airplaneIcon)
                        .flat(true) // Ensures the marker rotates with the map
                )
                markers[flight.callsign] = marker
            }
        }
    }

    private fun animateMarker(marker: Marker?, finalPosition: LatLng) {
        if (marker == null) return

        val startPosition = marker.position
        val handler = Handler(Looper.getMainLooper())
        val start = SystemClock.uptimeMillis()
        val duration: Long = 2000 // 2 seconds animation

        val interpolator = LinearInterpolator()
        val bearing = SphericalUtil.computeHeading(startPosition, finalPosition) // Calculate direction

        handler.post(object : Runnable {
            override fun run() {
                val elapsed = SystemClock.uptimeMillis() - start
                val t = interpolator.getInterpolation(elapsed.toFloat() / duration)

                // Interpolate position
                val lat = t * finalPosition.latitude + (1 - t) * startPosition.latitude
                val lng = t * finalPosition.longitude + (1 - t) * startPosition.longitude
                marker.position = LatLng(lat, lng)

                // Rotate marker to face the direction of travel
                marker.rotation = bearing.toFloat()

                if (t < 1.0) {
                    handler.postDelayed(this, 16)
                } else {
                    // Ensure the marker is at the final position and rotation after animation
                    marker.position = finalPosition
                    marker.rotation = bearing.toFloat()
                }
            }
        })
    }

    private fun trackFlight(marker: Marker) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 10f))
        handler.post(object : Runnable {
            override fun run() {
                val flight = flightDetailsMap[marker.title]
                flight?.let {
                    // Calculate the bearing for rotation based on the previous and current position
                    val previousPosition = marker.position
                    val newPosition = it.position
                    val bearing = SphericalUtil.computeHeading(previousPosition, newPosition)

                    marker.position = newPosition
                    marker.rotation = bearing.toFloat() // Rotate to match direction
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(newPosition))
                }
                handler.postDelayed(this, 2000) // Update every 2 seconds
            }
        })
    }

    private fun showFlightDetails(flight: FlightData) {
        val bottomSheet = FlightDetailsBottomSheet(flight)
        bottomSheet.show(parentFragmentManager, "FlightDetails")
    }
}