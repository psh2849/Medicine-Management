package com.example.healthcareapp.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcareapp.data.model.Type
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMedicineViewModel @Inject constructor(
) : ViewModel() {
    private val _addMedicineFirstImage = MutableLiveData<Uri>()
    val addMedicineFirstImage get() = _addMedicineFirstImage

    private val _addMedicineFirstName = MutableLiveData<String>()
    val addMedicineFirstName get() = _addMedicineFirstName

    private val _addMedicineFirstDescription = MutableLiveData<String>()
    val addMedicineFirstDescription get() = _addMedicineFirstDescription

    private val _addMedicineFirstDate = MutableLiveData<String>()
    val addMedicineFirstDate get() = _addMedicineFirstDate

    private val _buttonEvent = MutableLiveData<Boolean>()
    val buttonEvent get() = _buttonEvent

    fun enableFirstButton() {
        viewModelScope.launch {
            _buttonEvent.value =
                _addMedicineFirstImage.value != null &&
                        _addMedicineFirstName.value?.isNotEmpty() == true &&
                        _addMedicineFirstDescription.value?.isNotEmpty() == true &&
                        _addMedicineFirstDate.value?.isNotEmpty() == true
        }

    }
}