package com.example.healthcareapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.healthcareapp.data.model.Item

@Entity(tableName="favorite_table")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val item: Item
)
