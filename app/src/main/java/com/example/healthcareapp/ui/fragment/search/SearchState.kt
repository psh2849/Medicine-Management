package com.example.healthcareapp.ui.fragment.search

sealed class SearchState<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : SearchState<T>(data)
    class Fail<T>(message: String?, data: T? = null) : SearchState<T>(data, message)
    class Loading<T> : SearchState<T>()
}
