package com.example.healthcareapp.bindingadapter

import android.graphics.Color
import android.graphics.Typeface
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.healthcareapp.R
import java.text.SimpleDateFormat
import java.util.*

class AddMedicineBindingAdapter {

    companion object {
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

            if (getCalculateDate < 0) {
                textView.text = expire
                textView.setTextColor(Color.RED)
                textView.typeface = Typeface.DEFAULT_BOLD
            } else {
                textView.text = expire
                textView.setTextColor(Color.BLACK)
            }
        }

        @BindingAdapter("addMedicineButton")
        @JvmStatic
        fun setUpdate(button: Button, expire: String) {
            val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
            val expireDate = dateFormat.parse(expire)

            val today = Calendar.getInstance()
            val getCalculateDate = (expireDate!!.time - today.time.time) / (60 * 60 * 24 * 1000)

            button.isVisible = getCalculateDate < 0
        }
    }
}