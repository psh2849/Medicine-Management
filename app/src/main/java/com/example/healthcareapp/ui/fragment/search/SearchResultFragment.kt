package com.example.healthcareapp.ui.fragment.search

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.healthcareapp.R
import com.example.healthcareapp.databinding.FragmentSearchResultBinding
import com.example.healthcareapp.viewmodel.SearchMedicineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private val searchMedicineViewModel: SearchMedicineViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_medicine, menu)

        val search = menu.findItem(R.id.medicine_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(search: String?): Boolean {
        Log.d("SearchResultFragment", search.toString())
        search?.let {
            Log.d("SearchResultFragment", search.toString() + "here")
            searchMedicineViewModel.searchMedicine(searchMedicineViewModel.applySearchQueries(search))
            searchMedicineViewModel.searchMedicineResponse.observe(viewLifecycleOwner) { response ->
                Log.d("SearchResultFragment", response.body.items[0].itemName.toString())
            }
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        Log.d("SearchResultFragment", p0.toString())
        return true
    }


}