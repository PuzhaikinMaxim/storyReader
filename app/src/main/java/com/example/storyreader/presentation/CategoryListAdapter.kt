package com.example.storyreader.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storyreader.R
import com.example.storyreader.databinding.ItemCategoryBinding
import com.example.storyreader.databinding.ItemStoryBinding
import com.example.storyreader.domain.models.Category
import com.example.storyreader.domain.models.Story

class CategoryListAdapter: RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    var onItemClickedListener: ((Category)->Unit)? = null
    var categoryList: List<Category> = listOf()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = categoryList[position]
        with(holder) {
            binding.tvCategoryName.text = item.categoryName
            itemView.setOnClickListener {
                onItemClickedListener?.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CategoryViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root)
}