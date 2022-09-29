package com.example.healthcareapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcareapp.data.MedicineRepository
import com.example.healthcareapp.util.Constants.Companion.OUTPUT_TYPE
import com.example.healthcareapp.util.Constants.Companion.QUERY_ITEM_NAME
import com.example.healthcareapp.util.Constants.Companion.QUERY_SERVICE_KEY
import com.example.healthcareapp.util.Constants.Companion.QUERY_TYPE
import com.example.healthcareapp.util.Constants.Companion.SERVICE_KEY
import com.example.healthcareapp.ui.fragment.search.SearchState
import com.example.healthcareapp.util.Constants.Companion.QUERY_NUM_OF_ROWS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMedicineViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {

    private val _searchMedicineResponse: MutableStateFlow<SearchState> = MutableStateFlow(
        SearchState.Loading)
    val searchMedicineResponse  = _searchMedicineResponse.asStateFlow()

    fun searchMedicine(searchQuery: HashMap<String, String>) = viewModelScope.launch {
        val response = repository.remote.searchMedicine(searchQuery)

        if(response.isSuccessful) {
            _searchMedicineResponse.value = SearchState.Success(response.body())
        } else {
            _searchMedicineResponse.value = SearchState.Fail
            Log.d("SearchViewModel", response.message())
        }

    }

    fun applySearchQueries(search: String) : HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SERVICE_KEY] = SERVICE_KEY
        queries[QUERY_ITEM_NAME] = search
        queries[QUERY_TYPE] = OUTPUT_TYPE
        queries[QUERY_NUM_OF_ROWS] = "50"
        return queries
    }



}