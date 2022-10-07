package com.example.healthcareapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcareapp.data.MedicineRepository
import com.example.healthcareapp.data.model.Medicine
import com.example.healthcareapp.ui.fragment.search.SearchState
import com.example.healthcareapp.util.Constants.Companion.OUTPUT_TYPE
import com.example.healthcareapp.util.Constants.Companion.QUERY_ITEM_NAME
import com.example.healthcareapp.util.Constants.Companion.QUERY_NUM_OF_ROWS
import com.example.healthcareapp.util.Constants.Companion.QUERY_SERVICE_KEY
import com.example.healthcareapp.util.Constants.Companion.QUERY_TYPE
import com.example.healthcareapp.util.Constants.Companion.SERVICE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMedicineViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {

    private val _searchMedicineResponse: MutableLiveData<SearchState<Medicine>> = MutableLiveData()
    val searchMedicineResponse: LiveData<SearchState<Medicine>> get() = _searchMedicineResponse

    init {
        val map : HashMap<String, String> = applySearchQueries("a")

        viewModelScope.launch {
            getSearchedMedicine(map)
        }
    }

    fun searchMedicine(searchQuery: HashMap<String, String>) = viewModelScope.launch {
        getSearchedMedicine(searchQuery)
    }

    fun applySearchQueries(search: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SERVICE_KEY] = SERVICE_KEY
        queries[QUERY_ITEM_NAME] = search
        queries[QUERY_TYPE] = OUTPUT_TYPE
        queries[QUERY_NUM_OF_ROWS] = "50"

        return queries
    }

    private suspend fun getSearchedMedicine(searchQuery: HashMap<String, String>) {
        _searchMedicineResponse.value = SearchState.Loading()
        val response = repository.remote.searchMedicine(searchQuery)

        if (response.isSuccessful) {
            if (response.body()!!.body.total == 0) {
                _searchMedicineResponse.value = SearchState.Fail("결과를 조회할 수 없습니다.")
            } else {
                _searchMedicineResponse.value = SearchState.Success(response.body()!!)
            }
        } else {
            _searchMedicineResponse.value = SearchState.Fail(response.message())
            Log.d("SearchViewModel", response.message())
        }
    }

}