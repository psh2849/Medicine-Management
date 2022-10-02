package com.example.healthcareapp.ui.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthcareapp.adapter.SearchAdapter
import com.example.healthcareapp.databinding.FragmentSearchResultBinding
import com.example.healthcareapp.viewmodel.SearchMedicineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchView()
        initRecyclerView()
    }

    private fun initSearchView() {
        binding.searchViewSearchResult.clearFocus()
        binding.searchViewSearchResult.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(search: String?): Boolean {
                search?.let {
                    searchMedicine(search)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    private fun initRecyclerView() {
        binding.recyclerViewSearchResult.adapter = searchAdapter
        binding.recyclerViewSearchResult.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun searchMedicine(searchText: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
                searchMedicineViewModel.searchMedicine(
                    searchMedicineViewModel.applySearchQueries(
                        searchText
                    )
                )
                searchMedicineViewModel.searchMedicineResponse.collect { result ->
                    when (result) {
                        is SearchState.Success -> {
                            searchAdapter.submitList(result.medicine?.body?.items)
                            binding.recyclerViewSearchResult.adapter = searchAdapter
                            binding.progressBarSearchResult.visibility = View.INVISIBLE
                        }
                        is SearchState.Loading -> {
                            binding.progressBarSearchResult.visibility = View.VISIBLE
                        }
                        is SearchState.Fail -> {
                            binding.progressBarSearchResult.visibility = View.INVISIBLE
                            Toast.makeText(requireContext(), "불러 올 수 없습니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}