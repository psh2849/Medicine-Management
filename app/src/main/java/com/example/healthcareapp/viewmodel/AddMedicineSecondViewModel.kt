package com.example.healthcareapp.viewmodel

import androidx.lifecycle.*
import com.example.healthcareapp.data.MedicineRepository
import com.example.healthcareapp.data.database.entity.MedicineEntity
import com.example.healthcareapp.data.model.Type
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMedicineSecondViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {
    private val _typeMedicineList = MutableLiveData<ArrayList<Type>>()
    val typeMedicineList get() = _typeMedicineList
    private var typeItems = ArrayList<Type>()
    var saveType = ""

    val readMedicine: LiveData<List<MedicineEntity>> = repository.local.readMedicine().asLiveData()

    init {
        typeItems = arrayListOf(
            Type("캡슐", false),
            Type("정제", false),
            Type("액체", false),
            Type("국소성", false),
            Type("기기", false),
            Type("로션", false),
            Type("분무제", false),
            Type("연고", false),
            Type("점적", false),
            Type("젤", false),
            Type("좌약", false),
            Type("주사", false),
            Type("크림", false),
            Type("파우더", false),
            Type("패치", false),
            Type("폼", false),
            Type("흡입기", false),
        )
        _typeMedicineList.value = typeItems
    }

    fun checkedType(position: Int) {
        for (index in 0 until typeItems.size) {
            if (index == position) {
                typeItems[index].isCheck = true
                saveType = typeItems[index].type
            } else {
                typeItems[index].isCheck = false
            }
        }

        _typeMedicineList.value = typeItems
    }

    fun insertMedicineDatabase(medicineEntity: MedicineEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertMedicine(medicineEntity)
        }
    }
}