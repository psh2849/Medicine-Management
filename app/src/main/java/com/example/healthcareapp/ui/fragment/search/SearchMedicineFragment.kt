package com.example.healthcareapp.ui.fragment.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.healthcareapp.R
import com.example.healthcareapp.databinding.ActivityMainBinding.inflate
import com.example.healthcareapp.databinding.FragmentAddMedicineBinding.inflate
import com.example.healthcareapp.databinding.FragmentSearchMedicineBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMedicineFragment : Fragment() {

    private var _binding: FragmentSearchMedicineBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchMedicineBinding.inflate(inflater, container, false)

        binding.btnSearch.setOnClickListener {
            val action = SearchMedicineFragmentDirections.actionSearchMedicineFragmentToSearchResultFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }
}