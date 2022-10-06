package com.example.healthcareapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcareapp.data.model.Item
import com.example.healthcareapp.databinding.ItemSearchResultBinding
import com.example.healthcareapp.util.DiffUtils

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var mItemList = emptyList<Item>()

    class SearchViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(mItemList[position])
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    fun setData(newItemList: List<Item>) {
        val searchMedicineDiffUtil = DiffUtils(mItemList, newItemList)
        val diffUtilResult = DiffUtil.calculateDiff(searchMedicineDiffUtil)
        mItemList = newItemList
        diffUtilResult.dispatchUpdatesTo(this)
    }


}