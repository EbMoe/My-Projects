package com.alpha.aviatorview

import retrofit2.Response
import retrofit2.http.GET

interface OpenSkyApi {
    @GET("states/all")
    suspend fun getFlights(): Response<FlightData>
}

data class FlightData(val states: List<List<Any>>)
