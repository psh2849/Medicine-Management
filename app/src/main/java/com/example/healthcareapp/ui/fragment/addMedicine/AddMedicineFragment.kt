package com.example.healthcareapp.ui.fragment.addMedicine

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthcareapp.R
import com.example.healthcareapp.adapter.AddMedicineAdapter
import com.example.healthcareapp.data.database.entity.MedicineEntity
import com.example.healthcareapp.databinding.FragmentAddMedicineBinding
import com.example.healthcareapp.viewmodel.AddMedicineSecondViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddMedicineFragment : Fragment() {

    private val mAdapter by lazy { AddMedicineAdapter() }
    private val addMedicineSecondViewModel: AddMedicineSecondViewModel by viewModels()
    private var _binding: FragmentAddMedicineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddMedicineBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMenu()
        initViewModel()
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.add_medicine, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.medicine_add -> {
                        moveNextPage()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun moveNextPage() {
        val action =
            AddMedicineFragmentDirections.actionAddMedicineFragmentToAddMedicineFirstFragment()
        findNavController().navigate(action)
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
                addMedicineSecondViewModel.readMedicine.observe(viewLifecycleOwner) { entity ->
                    initRecyclerView(entity)
                }
            }
        }
    }

    private fun initRecyclerView(entity: List<MedicineEntity>) {
        mAdapter.submitList(entity)
        binding.recyclerviewAddMedicine.adapter = mAdapter
        binding.recyclerviewAddMedicine.layoutManager =
            LinearLayoutManager(requireContext())
    }
}