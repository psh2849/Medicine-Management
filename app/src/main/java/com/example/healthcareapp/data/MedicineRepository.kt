package com.example.healthcareapp.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class MedicineRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    val remote = remoteDataSource
}