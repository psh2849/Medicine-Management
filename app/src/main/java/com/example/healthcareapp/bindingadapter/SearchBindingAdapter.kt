package com.example.healthcareapp.bindingadapter

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.healthcareapp.R
import com.example.healthcareapp.data.model.Item
import com.example.healthcareapp.ui.fragment.search.SearchResultFragmentDirections

class SearchBindingAdapter {
    companion object {

        @BindingAdapter("onMedicineClickListener")
        @JvmStatic
        fun onMedicineClickListener(medicineRowLayout: ConstraintLayout, item: Item) {
            medicineRowLayout.setOnClickListener {
                try {
                    val action = SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(item)
                    medicineRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onMedicineClickListener", e.toString())
                }
            }
        }

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