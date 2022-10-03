package com.example.healthcareapp.data.database

import androidx.room.TypeConverter
import com.example.healthcareapp.data.database.entity.MedicineEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MedicineTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun medicineEntityToString(medicineEntity: MedicineEntity): String {
        return gson.toJson(medicineEntity)
    }

    @TypeConverter
    fun stringToMedicineEntity(medicine: String) : MedicineEntity {
        val listType = object : TypeToken<MedicineEntity>() {}.type
        return gson.fromJson(medicine, listType)
    }

}