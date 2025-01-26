package com.alpha.eventplanner

import retrofit2.Call
import retrofit2.http.*

interface JsonBinApiService {

    // Get all events from JSONBin
    @Headers("X-Master-Key: \$2a\$10\$J17LD8miDShnU3yIbi63EOgSiO61bxmcEI1QbyAelbl2C5ggfrsTu")
    @GET("b/66dffff5acd3cb34a8811b4b")
    fun getEvents(): Call<BinData>

    // Add a new event to JSONBin
    @Headers("Content-Type: application/json", "X-Master-Key: \$2a\$10\$J17LD8miDShnU3yIbi63EOgSiO61bxmcEI1QbyAelbl2C5ggfrsTu")
    @POST("b/66dffff5acd3cb34a8811b4b")
    fun addEvent(@Body event: Event): Call<Event>

    // Update the entire bin data
    @Headers("Content-Type: application/json", "X-Master-Key: \$2a\$10\$J17LD8miDShnU3yIbi63EOgSiO61bxmcEI1QbyAelbl2C5ggfrsTu")
    @PUT("b/66dffff5acd3cb34a8811b4b")
    fun updateEvents(@Body binData: BinData): Call<BinData>

    // Delete an event
    @Headers("X-Master-Key: \$2a\$10\$J17LD8miDShnU3yIbi63EOgSiO61bxmcEI1QbyAelbl2C5ggfrsTu")
    @DELETE("b/66dffff5acd3cb34a8811b4b")
    fun deleteEvent(@Query("id") id: String): Call<Void>
}
