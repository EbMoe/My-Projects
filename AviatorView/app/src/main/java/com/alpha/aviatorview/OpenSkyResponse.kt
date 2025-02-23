package com.alpha.aviatorview

data class OpenSkyResponse(
    val time: Long,                     // Timestamp of the response
    val states: List<List<Any>>?        // List of flight data
)
