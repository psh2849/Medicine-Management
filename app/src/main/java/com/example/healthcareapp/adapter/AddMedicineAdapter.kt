package com.example.healthcareapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcareapp.data.database.entity.MedicineEntity
import com.example.healthcareapp.databinding.ItemAddMedicineBinding

class AddMedicineAdapter :
    ListAdapter<MedicineEntity, AddMedicineAdapter.AddMedicineViewHolder>(diffCallback) {
    class AddMedicineViewHolder(private val binding: ItemAddMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicineEntity: MedicineEntity) {
            binding.medicineEntity = medicineEntity
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMedicineViewHolder {
        return AddMedicineViewHolder(
            ItemAddMedicineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AddMedicineViewHolder, position: Int) {
        getItem(position)?.let { entity ->
            holder.bind(entity)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<MedicineEntity>() {
            override fun areItemsTheSame(
                oldItem: MedicineEntity,
                newItem: MedicineEntity
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: MedicineEntity,
                newItem: MedicineEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}