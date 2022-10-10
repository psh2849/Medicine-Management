package com.example.healthcareapp.viewmodel

import androidx.lifecycle.*
import com.example.healthcareapp.data.MedicineRepository
import com.example.healthcareapp.data.database.entity.MedicineEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMedicineViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {

    val readMedicine: LiveData<List<MedicineEntity>> = repository.local.readMedicine().asLiveData()

    fun deleteMedicine(medicineEntity: MedicineEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteMedicine(medicineEntity)
        }
    }

    fun updateMedicine(medicineEntity: MedicineEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.updateMedicine(medicineEntity)
        }
    }
}