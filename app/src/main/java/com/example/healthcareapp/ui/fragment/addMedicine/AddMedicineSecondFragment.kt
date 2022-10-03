package com.example.healthcareapp.ui.fragment.addMedicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthcareapp.adapter.AddMedicineSecondAdapter
import com.example.healthcareapp.data.database.entity.MedicineEntity
import com.example.healthcareapp.databinding.FragmentAddMedicineSecondBinding
import com.example.healthcareapp.viewmodel.AddMedicineSecondViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddMedicineSecondFragment : Fragment() {

    private val addSecondViewModel: AddMedicineSecondViewModel by viewModels()
    private var _binding: FragmentAddMedicineSecondBinding? = null
    private val binding get() = _binding!!
    private val mAdapter: AddMedicineSecondAdapter by lazy { AddMedicineSecondAdapter() }
    private val args by navArgs<AddMedicineSecondFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddMedicineSecondBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun initClickListeners() {
        binding.buttonAddSecondNext.setOnClickListener {
            val medicineEntity = MedicineEntity(
                args.medicineEntity.id,
                args.medicineEntity.image,
                args.medicineEntity.name,
                args.medicineEntity.description,
                args.medicineEntity.expire,
                addSecondViewModel.saveType
            )

            addSecondViewModel.insertMedicineDatabase(medicineEntity)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initClickListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
                addSecondViewModel.typeMedicineList.observe(viewLifecycleOwner) { typeList ->
                    mAdapter.setItemClickListener(object :
                        AddMedicineSecondAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            addSecondViewModel.checkedType(position)
                            binding.buttonAddSecondNext.isEnabled = true
                        }
                    })

                    mAdapter.submitList(typeList)
                    binding.recyclerViewAddSecond.adapter = mAdapter
                    binding.recyclerViewAddSecond.layoutManager =
                        LinearLayoutManager(requireContext())
                }
            }
        }
    }
}