package com.alpha.aviatorview

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://opensky-network.org/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val openSkyApi: OpenSkyApi = retrofit.create(OpenSkyApi::class.java)
}
