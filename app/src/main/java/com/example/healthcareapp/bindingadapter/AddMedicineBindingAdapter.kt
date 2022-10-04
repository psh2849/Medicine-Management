package com.example.healthcareapp.bindingadapter

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.healthcareapp.R
import com.example.healthcareapp.data.database.entity.MedicineEntity
import com.example.healthcareapp.ui.fragment.addMedicine.AddMedicineFragmentDirections

class AddMedicineBindingAdapter {

    companion object {

        @BindingAdapter("addMedicineOnClick")
        @JvmStatic
        fun onClickLowLayout(constraintLayout: ConstraintLayout, medicineEntity: MedicineEntity) {
            constraintLayout.setOnClickListener {
                try {
                    val action =
                        AddMedicineFragmentDirections.actionAddMedicineFragmentToAddMedicineDetailFragment(
                            medicineEntity
                        )
                    constraintLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("AddMedicineBinding", e.toString())
                }

            }
        }

        @BindingAdapter("addMedicineSetImage")
        @JvmStatic
        fun setImage(imageView: ImageView, image: String) {
            Glide.with(imageView.context)
                .load(image)
                .placeholder(R.drawable.ic_error_placeholder)
                .centerCrop()
                .into(imageView)
        }

        @BindingAdapter("addMedicineName")
        @JvmStatic
        fun setName(textView: TextView, name: String) {
            textView.text = name
        }

        @BindingAdapter("addMedicineType")
        @JvmStatic
        fun setType(textView: TextView, type: String) {
            textView.text = type
        }

        @BindingAdapter("addMedicineExpire")
        @JvmStatic
        fun setExpire(textView: TextView, expire: String) {
            textView.text = expire
        }
    }
}