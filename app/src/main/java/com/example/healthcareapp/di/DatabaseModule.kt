package com.example.healthcareapp.di

import android.content.Context
import androidx.room.Room
import com.example.healthcareapp.data.database.MedicineDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MedicineDatabase::class.java,
        "medicine_database"
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: MedicineDatabase) = database.medicineDao()
}