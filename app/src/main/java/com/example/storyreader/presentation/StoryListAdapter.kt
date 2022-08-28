package com.example.storyreader.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.storyreader.R
import com.example.storyreader.databinding.ItemStoryBinding
import com.example.storyreader.domain.models.Story
import com.example.storyreader.presentation.viewmodels.StoryListDiffCallback

class StoryListAdapter: RecyclerView.Adapter<StoryListAdapter.StoryViewHolder>() {

    var onItemClickedListener: ((Story)->Unit)? = null

    var onLongClickListener: ((Story)-> Unit)? = null

    var storyList: List<Story> = listOf()
        set(value) {
            val callback = StoryListDiffCallback(storyList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val item = storyList[position]
        with(holder) {
            binding.tvStoryName.text = item.storyName
            val image = when(item.isFavourite){
                true -> {R.drawable.star}
                false -> {R.drawable.star_empty}
            }
            binding.ivFavourite.setImageResource(image)
            itemView.setOnClickListener {
                onItemClickedListener?.invoke(item)
            }
            itemView.setOnLongClickListener {
                onLongClickListener?.invoke(item)
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return storyList.size
    }

    class StoryViewHolder(val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root)
}