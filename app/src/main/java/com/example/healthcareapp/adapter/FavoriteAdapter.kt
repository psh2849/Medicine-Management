package com.example.healthcareapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcareapp.data.database.entity.FavoriteEntity
import com.example.healthcareapp.databinding.ItemFavoriteBinding
import com.example.healthcareapp.databinding.ItemSearchResultBinding
import com.example.healthcareapp.util.DiffUtils

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favoriteList = emptyList<FavoriteEntity>()

    class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoriteEntity) {
            binding.item = item.item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            ItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    fun setData(newFavoriteList: List<FavoriteEntity>) {
        val favoriteDiffUtil = DiffUtils(favoriteList, newFavoriteList)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteDiffUtil)

        favoriteList = newFavoriteList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}