package com.example.healthcareapp.ui.fragment.addMedicine

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.healthcareapp.databinding.FragmentAddMedicineFirstBinding
import com.example.healthcareapp.util.Constants
import com.example.healthcareapp.viewmodel.AddMedicineViewModel
import kotlinx.coroutines.launch
import java.util.*

class AddMedicineFirstFragment : Fragment() {

    private val addMedicineViewModel: AddMedicineViewModel by viewModels()

    private var _binding: FragmentAddMedicineFirstBinding? = null
    private val binding get() = _binding!!

    private val imageResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                addMedicineViewModel.addMedicineFirstImage.value = result.data?.data
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddMedicineFirstBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListener()
        initViewModel()
    }

    private fun initViews() {
        binding.ImageViewAddImage.setOnClickListener {
            getImageFromGallery()
        }

        binding.buttonAddFirstDate.setOnClickListener {
            getDatePicker()
        }

        binding.buttonAddFirstNext.setOnClickListener {
            if (addMedicineViewModel.buttonEvent.value == true) {

            }
        }


    }

    private fun initListener() {
        binding.editTextAddFirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                addMedicineViewModel.addMedicineFirstName.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.editTextAddFirstExplain.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                addMedicineViewModel.addMedicineFirstDescription.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }


    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
                addMedicineViewModel.addMedicineFirstName.observe(viewLifecycleOwner) {
                    verifyNextButton()
                }

                addMedicineViewModel.addMedicineFirstImage.observe(viewLifecycleOwner) { uri ->
                    Glide.with(requireContext())
                        .load(uri)
                        .centerCrop()
                        .into(binding.ImageViewAddImage)
                    verifyNextButton()
                }

                addMedicineViewModel.addMedicineFirstDescription.observe(viewLifecycleOwner) {
                    verifyNextButton()
                }

                addMedicineViewModel.addMedicineFirstDate.observe(viewLifecycleOwner) { date ->
                    binding.buttonAddFirstDate.text = date
                    verifyNextButton()
                }
            }
        }
    }

    private fun getImageFromGallery() {
        val readPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (readPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), Constants.REQUEST_CODE
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")

            imageResultLauncher.launch(intent)
        }
    }

    private fun verifyNextButton() {
        addMedicineViewModel.enableFirstButton()
        addMedicineViewModel.buttonEvent.value?.let { result ->
            binding.buttonAddFirstNext.isEnabled = result
        }
    }

    private fun getDatePicker() {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, changeYear, changeMonth, changeDay ->
                addMedicineViewModel.addMedicineFirstDate.value =
                    "$changeYear.$changeMonth.$changeDay"
            }
        DatePickerDialog(requireContext(), dateSetListener, year, month, day).show()
    }
}
