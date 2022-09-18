package com.example.healthcareapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcareapp.data.MedicineRepository
import com.example.healthcareapp.data.model.Medicine
import com.example.healthcareapp.util.Constants
import com.example.healthcareapp.util.Constants.Companion.OUTPUT_TYPE
import com.example.healthcareapp.util.Constants.Companion.QUERY_ITEM_NAME
import com.example.healthcareapp.util.Constants.Companion.QUERY_SERVICE_KEY
import com.example.healthcareapp.util.Constants.Companion.QUERY_TYPE
import com.example.healthcareapp.util.Constants.Companion.SERVICE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMedicineViewModel @Inject constructor(
    application: Application,
    private val repository: MedicineRepository
) : ViewModel() {

    var searchMedicineResponse: MutableLiveData<Medicine> = MutableLiveData()

    fun searchMedicine(searchQuery: HashMap<String, String>) = viewModelScope.launch {
        val response = repository.remote.searchMedicine(searchQuery)

        if(response.isSuccessful) {
            searchMedicineResponse.value = response.body()
        } else {
            Log.d("SearchViewModel", response.message())
        }

    }

    fun applySearchQueries(search: String) : HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SERVICE_KEY] = SERVICE_KEY
        queries[QUERY_ITEM_NAME] = search
        queries[QUERY_TYPE] = OUTPUT_TYPE

        return queries
    }



}