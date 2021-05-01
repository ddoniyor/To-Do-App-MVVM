package com.example.todoappmvvm.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.todoappmvvm.model.retrofit.Pojo

open class ListsDiffUtil<T>(private val oldItems: List<T>, private val newItems: List<T>) : DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemPosition == newItemPosition
    }

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}