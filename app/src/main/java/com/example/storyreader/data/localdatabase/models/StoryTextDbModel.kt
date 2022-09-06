package com.example.storyreader.data.localdatabase.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "storyText"
)
data class StoryTextDbModel(
    @PrimaryKey
    val storyId: Int,
    val storyText: String
)