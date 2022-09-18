package com.example.healthcareapp.data.model

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("entpName")
    val companyName: String?,
    @SerializedName("itemName")
    val itemName: String?,
    @SerializedName("itemSeq")
    val itemCode: String?,
    @SerializedName("efcyQesitm")
    val efficacy: String?,
    @SerializedName("useMethodQesitm")
    val instruction: String?,
    @SerializedName("atpnWarnQesitm")
    val cautionBeforeUsing: String?,
    @SerializedName("atpnQesitm")
    val caution: String?,
    @SerializedName("intrcQesitm")
    val interaction: String?,
    @SerializedName("seQesitm")
    val sideEffect: String?,
    @SerializedName("depositMethodQesitm")
    val storageMethod: String?,
    @SerializedName("openDe")
    val openDate: String?,
    @SerializedName("updateDe")
    val updateDate: String?,
    @SerializedName("itemImage")
    val itemImage: String?
)