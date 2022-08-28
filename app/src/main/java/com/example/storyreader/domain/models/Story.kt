package com.example.storyreader.domain.models

data class Story(
    val storyId: Int,
    val storyName: String,
    val storyText: String?,
    val isFavourite: Boolean = false,
    val isRead: Boolean = false
)
