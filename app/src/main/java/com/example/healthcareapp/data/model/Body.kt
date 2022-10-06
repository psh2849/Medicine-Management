package com.example.healthcareapp.data.model


import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("totalCount")
    val total: Int
)