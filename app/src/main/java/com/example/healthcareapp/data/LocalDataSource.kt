package com.example.healthcareapp.data

import com.example.healthcareapp.data.database.MedicineDao
import com.example.healthcareapp.data.database.entity.FavoriteEntity
import com.example.healthcareapp.data.database.entity.MedicineEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val medicineDao: MedicineDao
) {
    fun readMedicine(): Flow<List<MedicineEntity>> {
        return medicineDao.readMedicine()
    }

    suspend fun insertMedicine(medicineEntity: MedicineEntity) {
        medicineDao.insertMedicine(medicineEntity)
    }

    suspend fun deleteMedicine(medicineEntity: MedicineEntity) {
        medicineDao.deleteMedicine(medicineEntity)
    }

    suspend fun updateMedicine(medicineEntity: MedicineEntity) {
        medicineDao.updateMedicine(medicineEntity)
    }

    fun readFavoriteMedicine(): Flow<List<FavoriteEntity>> {
        return medicineDao.readFavoriteMedicine()
    }

    suspend fun insertFavoriteMedicine(favoriteEntity: FavoriteEntity) {
        medicineDao.insertFavoriteMedicine(favoriteEntity)
    }

    suspend fun deleteFavoriteMedicine(favoriteEntity: FavoriteEntity) {
        medicineDao.deleteFavoriteMedicine(favoriteEntity)
    }
}