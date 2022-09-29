package com.example.healthcareapp.ui.fragment.search

import com.example.healthcareapp.data.model.Medicine

sealed class SearchState {
    data class Success(val medicine: Medicine?) : SearchState()
    object Fail: SearchState()
    object Loading: SearchState()
}
