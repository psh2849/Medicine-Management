package com.example.healthcareapp.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "medicine_table")
@Parcelize
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val image: String,
    val name: String,
    val description: String,
    var expire: String,
    val type: String?
) : Parcelable



