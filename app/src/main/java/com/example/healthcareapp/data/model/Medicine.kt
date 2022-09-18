package com.example.healthcareapp.data.model


import com.google.gson.annotations.SerializedName

data class Medicine(
    @SerializedName("body")
    val body: Body,
    @SerializedName("header")
    val header: Header
)