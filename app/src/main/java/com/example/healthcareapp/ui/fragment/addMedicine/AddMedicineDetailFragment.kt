package com.example.healthcareapp.ui.fragment.addMedicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.healthcareapp.R
import com.example.healthcareapp.databinding.FragmentAddMedicineDetailBinding

class AddMedicineDetailFragment : Fragment() {

    private var _binding: FragmentAddMedicineDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddMedicineDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddMedicineDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        Glide.with(requireContext())
            .load(args.medicineEntity.image)
            .centerCrop()
            .error(R.drawable.ic_error_placeholder)
            .into(binding.imageViewAddDetailMainImage)

        binding.textViewAddDetailTitle.text = args.medicineEntity.name
        binding.textViewAddDetailType.text = args.medicineEntity.type
        binding.textViewAddDetailExpire.text = args.medicineEntity.expire
        binding.textViewAddDetailUse.text = args.medicineEntity.description
    }
}