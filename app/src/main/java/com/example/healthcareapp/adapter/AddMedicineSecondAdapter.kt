package com.example.healthcareapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcareapp.data.database.entity.MedicineEntity
import com.example.healthcareapp.data.model.Type
import com.example.healthcareapp.databinding.ItemAddSecondBinding
import com.example.healthcareapp.util.DiffUtils

class AddMedicineSecondAdapter :
    RecyclerView.Adapter<AddMedicineSecondAdapter.AddSecondViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    private var mTypeItemList = emptyList<Type>()

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    class AddSecondViewHolder(private val binding: ItemAddSecondBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(type: Type) {
            binding.type = type
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSecondViewHolder {
        return AddSecondViewHolder(
            ItemAddSecondBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AddSecondViewHolder, position: Int) {
        holder.bind(mTypeItemList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return mTypeItemList.size
    }

    fun setData(newTypeItem: List<Type>) {
        val addMedicineDiffUtil = DiffUtils(mTypeItemList, newTypeItem)
        val diffUtilResult = DiffUtil.calculateDiff(addMedicineDiffUtil)
        mTypeItemList = newTypeItem
        diffUtilResult.dispatchUpdatesTo(this)
    }

}