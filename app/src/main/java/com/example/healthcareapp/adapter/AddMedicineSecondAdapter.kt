package com.example.healthcareapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcareapp.data.model.Type
import com.example.healthcareapp.databinding.ItemAddSecondBinding

class AddMedicineSecondAdapter :
    ListAdapter<Type, AddMedicineSecondAdapter.AddSecondViewHolder>(diffCallback) {

    private lateinit var itemClickListener: OnItemClickListener

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
        getItem(position)?.let { type ->
            holder.bind(type)
        }
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Type>() {
            override fun areItemsTheSame(oldItem: Type, newItem: Type): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Type, newItem: Type): Boolean {
                return oldItem == newItem
            }
        }
    }
}