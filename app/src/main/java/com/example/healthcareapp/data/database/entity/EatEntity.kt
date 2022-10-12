package com.example.healthcareapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "eat_table")
data class EatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val medicineEntity: MedicineEntity
)