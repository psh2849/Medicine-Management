package com.example.healthcareapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.healthcareapp.data.database.entity.MedicineEntity

@Database(
    entities = [MedicineEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(MedicineTypeConverter::class)
abstract class MedicineDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
}