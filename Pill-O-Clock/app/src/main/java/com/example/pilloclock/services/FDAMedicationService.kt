package com.example.pilloclock.services

import com.example.example.FDAMedicationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FDAMedicationService {
    @GET("drug/label.json?")
    fun getMedicationDetails(@Query("limit") limit: String,
                             @Query("search") search: String): Call<FDAMedicationResponse>
}