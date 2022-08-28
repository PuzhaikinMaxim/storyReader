package com.example.storyreader.data.localdatabase.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.storyreader.domain.models.Category

@Entity(
    tableName = "Story"
)
data class StoryDbModel(
    @PrimaryKey val storyId: Int,
    val storyName: String,
    val storyText: String?,
    val isFavourite: Boolean = false,
    val isRead: Boolean = false
)