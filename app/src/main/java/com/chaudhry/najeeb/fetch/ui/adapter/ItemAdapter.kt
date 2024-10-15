package com.chaudhry.najeeb.fetch.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chaudhry.najeeb.fetch.data.Item
import com.chaudhry.najeeb.fetch.databinding.ItemViewBinding

//This ItemAdapter class extends ListAdapter, which is a specialized adapter for RecyclerView.
// The ListAdapter class is part of the Android Jetpack library and is designed to handle list data efficiently
// with the help of DiffUtil.
class ItemAdapter : ListAdapter<Item, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {

    //This method sets the views to display the items
    //This method is called each time a new ViewHolder is needed .  This typically happens when
    // the RecyclerView needs to display a new item that doesn't have a recycled ViewHolder available
    // In other words, onCreateViewHolder is called multiple times, but only as many times as needed to create
    // enough ViewHolder instances to display the visible items plus a few extra for smooth scrolling
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //Log.d(TAG, "onCreateViewHolder() called")
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    //This method binds the data to the views when in MainActivity submitList(items) is called
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //Log.d(TAG, "Binding item at position $position: ${getItem(position)}")
        holder.bind(getItem(position))
    }

    //This class is the ViewHolder for the RecyclerView.  It binds each Item to the view using data binding
    class ItemViewHolder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            //Log.d("ItemViewHolder", "Binding item: $item")
            binding.item = item
            binding.executePendingBindings()
        }
    }

    //This class is used to update the items in a RecyclerView by calculating the differences between
    // the old and new lists
    class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
        //This method checks if the contents of the items are the same, same ID
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        //This method checks if the contents of two items are the same
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val TAG = "ItemAdapter"
    }
}