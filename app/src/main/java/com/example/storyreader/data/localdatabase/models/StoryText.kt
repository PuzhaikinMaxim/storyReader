package com.example.storyreader.data.localdatabase.models

data class StoryText(
    val storyId: Int,
    val storyName: String,
    val storyText: String,
    val isFavourite: Boolean = false,
    val isRead: Boolean = false
) {
}