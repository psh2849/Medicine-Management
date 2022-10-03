package com.example.healthcareapp.data.database

import androidx.room.*
import com.example.healthcareapp.data.database.entity.MedicineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicineEntity: MedicineEntity)

    @Query("SELECT * FROM medicine_table ORDER BY id ASC")
    fun readMedicine(): Flow<List<MedicineEntity>>

    @Delete
    suspend fun deleteMedicine(medicineEntity: MedicineEntity)
}