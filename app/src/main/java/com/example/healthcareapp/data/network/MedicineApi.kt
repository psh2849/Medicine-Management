package com.example.healthcareapp.data.network

import com.example.healthcareapp.data.model.Medicine
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface MedicineApi {

    @GET("getDrbEasyDrugList")
    suspend fun searchMedicine(
        @QueryMap(encoded = true) queries: Map<String, String>
    ): Response<Medicine>

}