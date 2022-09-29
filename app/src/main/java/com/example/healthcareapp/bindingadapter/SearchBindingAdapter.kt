package com.example.healthcareapp.bindingadapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.healthcareapp.R

class SearchBindingAdapter {
    companion object {

        @BindingAdapter("loadImageUrl")
        @JvmStatic
        fun loadImageUrl(imageView: ImageView, imageUrl: String?) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .error(R.drawable.ic_error_placeholder)
                .placeholder(R.drawable.ic_error_placeholder)
                .fitCenter()
                .into(imageView)
        }

        @BindingAdapter("setMedicineTitle")
        @JvmStatic
        fun setMedicineTitle(textView: TextView, title: String) {
            textView.text = title
        }

        @BindingAdapter("setMedicineCompany")
        @JvmStatic
        fun setMedicineCompany(textView: TextView, company: String) {
            textView.text = company
        }
    }
}