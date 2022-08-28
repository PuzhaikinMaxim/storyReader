package com.example.storyreader.data.localdatabase.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["storyId", "categoryId"])
data class StoryCategoryCrossRef(
    val storyId: Int,
    val categoryId: Int
)
