package com.example.healthcareapp.data

import com.example.healthcareapp.data.model.Medicine
import com.example.healthcareapp.data.network.MedicineApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val medicineApi: MedicineApi
) {
    suspend fun searchMedicine(
        queries: Map<String, String>
    ): Response<Medicine> {
        return medicineApi.searchMedicine(queries)
    }
}