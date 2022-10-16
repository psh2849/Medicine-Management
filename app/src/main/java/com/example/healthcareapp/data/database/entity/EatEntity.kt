package com.example.healthcareapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "eat_table")
data class EatEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "eat_id") val id: Int = 0,
    val date: String,
    @Embedded val medicineEntity: MedicineEntity
)