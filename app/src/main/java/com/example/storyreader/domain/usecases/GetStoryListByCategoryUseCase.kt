package com.example.storyreader.domain.usecases

import androidx.lifecycle.LiveData
import com.example.storyreader.domain.StoryRepository
import com.example.storyreader.domain.models.Story
import javax.inject.Inject

class GetStoryListByCategoryUseCase @Inject constructor(private val repository: StoryRepository) {

    suspend operator fun invoke(categoryId: Int): LiveData<List<Story>> {
        return repository.getStoriesByCategory(categoryId)
    }
}