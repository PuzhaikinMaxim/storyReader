package com.example.storyreader.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.storyreader.domain.models.Story

class StoryListDiffCallback(
    private val oldList: List<Story>,
    private val newList: List<Story>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].storyId == newList[newItemPosition].storyId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}