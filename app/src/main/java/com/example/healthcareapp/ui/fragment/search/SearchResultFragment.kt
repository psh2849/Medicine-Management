package com.example.healthcareapp.ui.fragment.search

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthcareapp.R
import com.example.healthcareapp.adapter.SearchAdapter
import com.example.healthcareapp.databinding.FragmentSearchResultBinding
import com.example.healthcareapp.viewmodel.SearchMedicineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private val searchMedicineViewModel: SearchMedicineViewModel by viewModels()
    private val searchAdapter by lazy { SearchAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerViewSearch.adapter = searchAdapter
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_medicine, menu)

        val search = menu.findItem(R.id.medicine_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(search: String?): Boolean {
        search?.let {
            searchMedicine(search)
        }
        return true
    }

    private fun searchMedicine(searchText: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
                searchMedicineViewModel.searchMedicine(searchMedicineViewModel.applySearchQueries(searchText))
                searchMedicineViewModel.searchMedicineResponse.collect { result ->
                    when(result) {
                        is SearchState.Success -> {
                            // Log.d("SearchResultFragment", result.medicine.body.items[0].itemName)
                            searchAdapter.submitList(result.medicine?.body?.items)
                            binding.recyclerViewSearch.adapter = searchAdapter
                        }
                        is SearchState.Loading -> {
                            Log.d("SearchResultFragment", "Loading")
                        }
                        is SearchState.Fail -> {
                            Log.d("SearchResultFragment", "Fail")
                        }
                    }
                }
            }
        }
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        Log.d("SearchResultFragment", p0.toString())
        return true
    }



}