package com.example.healthcareapp.viewmodel

import androidx.lifecycle.*
import com.example.healthcareapp.data.MedicineRepository
import com.example.healthcareapp.data.database.entity.EatEntity
import com.example.healthcareapp.data.database.entity.MedicineEntity
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList
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

    fun insertEatMedicine(eatEntity: EatEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertEatMedicine(eatEntity)
        }
    }

    fun getEatMedicineMonth(today: String, id: Int): LiveData<List<EatEntity>> {
        return repository.local.getEatMedicineMonth(today, id).asLiveData()
    }
}