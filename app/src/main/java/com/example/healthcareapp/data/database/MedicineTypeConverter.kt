package com.example.healthcareapp.data.database

import androidx.room.TypeConverter
import com.example.healthcareapp.data.database.entity.EatEntity
import com.example.healthcareapp.data.database.entity.FavoriteEntity
import com.example.healthcareapp.data.database.entity.MedicineEntity
import com.example.healthcareapp.data.model.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

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

    @TypeConverter
    fun favoriteEntityToString(item: Item): String {
        return gson.toJson(item)
    }

    @TypeConverter
    fun stringToFavoriteEntity(favorite: String) : Item {
        val listType = object : TypeToken<Item>() {}.type
        return gson.fromJson(favorite, listType)
    }

}