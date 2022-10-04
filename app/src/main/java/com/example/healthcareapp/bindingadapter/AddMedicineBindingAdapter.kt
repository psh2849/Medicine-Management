package com.example.healthcareapp.bindingadapter

import android.graphics.Color
import android.graphics.Typeface
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
import java.text.SimpleDateFormat
import java.util.*

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
            val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
            val expireDate = dateFormat.parse(expire)

            val today = Calendar.getInstance()
            val getCalculateDate = (expireDate!!.time - today.time.time) / (60 * 60 * 24 * 1000)

            if(getCalculateDate - 1 < 0) {
                textView.text = expire
                textView.setTextColor(Color.RED)
                textView.typeface = Typeface.DEFAULT_BOLD
            } else {
                textView.text = expire
                textView.setTextColor(Color.BLACK)
            }
        }
    }
}