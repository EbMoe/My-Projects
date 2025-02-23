package com.alpha.aviatorview

import com.google.android.gms.maps.model.LatLng

data class FlightData(
    val callsign: String,
    val origin: String?, // e.g., "CPT" for Cape Town
    val destination: String?, // e.g., "JNB" for Johannesburg
    val altitude: Double,
    val speed: Double,
    val aircraft: String?,
    val position: LatLng
)