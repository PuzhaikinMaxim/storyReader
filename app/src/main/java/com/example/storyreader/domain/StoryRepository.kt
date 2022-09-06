package com.example.storyreader.domain

import androidx.lifecycle.LiveData
import com.example.storyreader.domain.models.Category
import com.example.storyreader.domain.models.Story

interface StoryRepository {

    fun getStoriesList(): LiveData<List<Story>>

    fun getCategoriesList(): LiveData<List<Category>>

    suspend fun getStoriesByCategory(categoryId: Int): LiveData<List<Story>>

    suspend fun getStory(id: Int): Story

    suspend fun markRead(id: Int)

    suspend fun addInFavourite(story: Story)

    suspend fun getFavouriteStories(): LiveData<List<Story>>
}