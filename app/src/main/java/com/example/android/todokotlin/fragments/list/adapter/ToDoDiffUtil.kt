package com.example.android.todokotlin.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.android.todokotlin.data.model.ToDoData

/**
 * to help list adapter
 */
class ToDoDiffUtil(private val oldList : List<ToDoData>, private val newList : List<ToDoData>):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    /**
     * called by diff util to check if 2 items represent the same object.
     * if items have different ids, then we should check the id equality
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // === mean items are indentical or not
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    /**
     * this is only called by DIffUtil when areItemsTheSame return true
     * check if two data have the same information or variables, the behaviour can be change depends
     * on how we want to implement it
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id &&
                oldList[oldItemPosition].title == newList[newItemPosition].title &&
                oldList[oldItemPosition].description == newList[newItemPosition].description &&
                oldList[oldItemPosition].priority == newList[newItemPosition].priority
    }
}