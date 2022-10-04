package com.example.healthcareapp.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "medicine_table")
@Parcelize
class MedicineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val image: String,
    val name: String,
    val description: String,
    val expire: String,
    val type: String?
) : Parcelable


