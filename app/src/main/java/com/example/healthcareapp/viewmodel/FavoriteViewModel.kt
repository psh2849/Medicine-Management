package com.example.healthcareapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.healthcareapp.data.MedicineRepository
import com.example.healthcareapp.data.database.entity.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {

    val readFavoriteMedicine: LiveData<List<FavoriteEntity>> =
        repository.local.readFavoriteMedicine().asLiveData()

    fun insertFavoriteMedicine(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteMedicine(favoriteEntity)
        }
    }

    fun deleteFavoriteMedicine(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteMedicine(favoriteEntity)
        }
    }
}