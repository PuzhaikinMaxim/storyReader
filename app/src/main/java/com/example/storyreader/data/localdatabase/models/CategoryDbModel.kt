package com.example.storyreader.data.localdatabase.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryDbModel(
    @PrimaryKey val categoryId: Int,
    val categoryName: String
)