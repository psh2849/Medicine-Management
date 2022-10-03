package com.example.healthcareapp.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

class AddMedicineSecondBindingAdapter {

    companion object {

        @BindingAdapter("setType")
        @JvmStatic
        fun setType(textView: TextView, type: String) {
            textView.text = type
        }

        @BindingAdapter("setImage")
        @JvmStatic
        fun setImage(imageView: ImageView, check: Boolean) {
            if (check) {
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.INVISIBLE
            }
        }
    }
}