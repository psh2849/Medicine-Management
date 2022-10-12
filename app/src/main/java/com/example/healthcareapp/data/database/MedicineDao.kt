package com.example.healthcareapp.data.database

import androidx.room.*
import com.example.healthcareapp.data.database.entity.EatEntity
import com.example.healthcareapp.data.database.entity.FavoriteEntity
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

    @Update
    suspend fun updateMedicine(medicineEntity: MedicineEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMedicine(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_table ORDER BY id ASC")
    fun readFavoriteMedicine(): Flow<List<FavoriteEntity>>

    @Delete
    suspend fun deleteFavoriteMedicine(favoriteEntity: FavoriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEatMedicine(eatEntity: EatEntity)

    @Query("SELECT * FROM eat_table, medicine_table " +
            "WHERE :today LIKE substr(date, 1, 7) " +
            "AND medicine_table.id = :id")
    fun getEatMedicineMonth(today: String, id: Int): Flow<List<EatEntity>>
}