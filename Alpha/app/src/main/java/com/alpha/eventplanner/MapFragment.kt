package com.alpha.eventplanner

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
import com.alpha.eventplanner.databinding.FragmentMapBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.*
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat

class MapFragment : Fragment() {

    private val locationRequestCode = 0
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private var mapView: MapView? = null
    private lateinit var mapController: IMapController
    private lateinit var viewPager: ViewPager2
    private val notifiedLocations = mutableSetOf<String>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("event_prefs", Context.MODE_PRIVATE)
        notifiedLocations.addAll(sharedPreferences.getStringSet("notifiedLocations", setOf()) ?: setOf())

        if (isOnline()) {
            Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
            setupMap()
            loadEventLocations()
            managePermissions()
            disableSwipeOnMap()
        } else {
            Toast.makeText(requireContext(), "You need to be online to access the map.", Toast.LENGTH_SHORT).show()
            binding.mapView.visibility = View.GONE
        }
    }

    private fun setupMap() {
        mapView = binding.mapView
        mapController = mapView!!.controller
        mapView!!.setMultiTouchControls(true)
        mapView!!.onResume()
        val startPoint = GeoPoint(-29.8587, 31.0218)
        mapController.setCenter(startPoint)
        mapController.setZoom(6.0)
    }

    override fun onResume() {
        super.onResume()
        if (mapView != null && isOnline()) {
            mapView!!.onResume()
            refreshMap()
        }
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
        sharedPreferences.edit().putStringSet("notifiedLocations", notifiedLocations).apply()
    }

    private fun refreshMap() {
        mapView?.let {
            mapController.setCenter(GeoPoint(-29.8587, 31.0218))
            it.invalidate()
        }
    }

    private fun disableSwipeOnMap() {
        viewPager = requireActivity().findViewById(R.id.viewPager)
        mapView?.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> viewPager.isUserInputEnabled = false
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> viewPager.isUserInputEnabled = true
            }
            false
        }
    }

    private fun loadEventLocations() {
        val eventsJson = sharedPreferences.getString("events", null)
        eventsJson?.let {
            val gson = Gson()
            val type = object : TypeToken<List<Event>>() {}.type
            val events: List<Event> = gson.fromJson(it, type)
            events.forEach { event ->
                if (event.location.isNotEmpty()) {
                    getLocationFromAddress(event.location)?.let { geoPoint ->
                        val locationKey = "${geoPoint.latitude},${geoPoint.longitude}"
                        addMarker(geoPoint, event.title, notifiedLocations.add(locationKey))
                    } ?: Toast.makeText(requireContext(), "Could not find location: ${event.location}", Toast.LENGTH_SHORT).show()
                }
            }
        } ?: Toast.makeText(requireContext(), "No events found", Toast.LENGTH_SHORT).show()
    }

    private fun getLocationFromAddress(location: String): GeoPoint? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        return geocoder.getFromLocationName(location, 1)?.let { addresses ->
            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                GeoPoint(address.latitude, address.longitude)
            } else null
        }
    }

    private fun addMarker(location: GeoPoint, title: String, isNew: Boolean) {
        mapView?.let {
            val marker = Marker(it)
            marker.position = location
            marker.title = title
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.icon = ContextCompat.getDrawable(requireContext(), R.drawable.mp)
            it.overlays.add(marker)
            it.invalidate()

            if (isNew) {
                triggerNotification("New Location Added", "A new marker has been added for: $title")
            }
        }
    }

    private fun managePermissions() {
        val requestPermissions = mutableListOf<String>()
        if (!isLocationPermissionGranted()) {
            requestPermissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!hasStoragePermission()) {
            requestPermissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (requestPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(requireActivity(), requestPermissions.toTypedArray(), locationRequestCode)
        }
    }

    private fun isLocationPermissionGranted(): Boolean =
        ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasStoragePermission(): Boolean =
        ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationRequestCode && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onPause()
        _binding = null
    }

    private fun isOnline(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private fun triggerNotification(title: String, message: String) {
        val notificationManager = ContextCompat.getSystemService(requireContext(), NotificationManager::class.java) as NotificationManager
        val notificationChannelId = "unique_map_notification_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(notificationChannelId, "Map Event Notifications", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(requireContext(), notificationChannelId)
            .setSmallIcon(R.drawable.alpine)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(Random().nextInt(), notificationBuilder.build())
    }
}