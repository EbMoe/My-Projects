package com.alpha.eventplanner


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Base URL for JSONBin API
    private const val BASE_URL = "https://api.jsonbin.io/v3/" // JSONBin Base URL

    // Create Retrofit instance
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Convert JSON data into Kotlin objects
            .build()
    }

    // Create an instance of the JSONBin API service
    val api: JsonBinApiService by lazy {
        retrofit.create(JsonBinApiService::class.java)
    }
}
