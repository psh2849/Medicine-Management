package com.example.healthcareapp.ui.fragment.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthcareapp.adapter.SearchAdapter
import com.example.healthcareapp.data.model.Item
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
        initViewModels()
    }

    private fun initViewModels() {
        lifecycleScope.launch {
            searchMedicineViewModel.searchMedicineResponse.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is SearchState.Success -> {
                        result.data?.body?.items?.let {
                            initRecyclerView(it)
                            binding.recyclerViewSearchResult.visibility = View.VISIBLE
                            binding.progressBarSearchResult.visibility = View.INVISIBLE
                            binding.textViewSearchResultNoResult.visibility = View.INVISIBLE
                        }
                    }

                    is SearchState.Loading -> {
                        binding.progressBarSearchResult.visibility = View.VISIBLE
                    }

                    is SearchState.Fail -> {
                        binding.progressBarSearchResult.visibility = View.INVISIBLE
                        binding.recyclerViewSearchResult.visibility = View.INVISIBLE
                        binding.textViewSearchResultNoResult.visibility = View.VISIBLE

                    }
                }
            }
        }
    }

    private fun initSearchView() {
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

    private fun initRecyclerView(item: List<Item>) {
        searchAdapter.setData(item)

        binding.recyclerViewSearchResult.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun searchMedicine(searchText: String) {
        searchMedicineViewModel.searchMedicine(
            searchMedicineViewModel.applySearchQueries(
                searchText
            )
        )
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}