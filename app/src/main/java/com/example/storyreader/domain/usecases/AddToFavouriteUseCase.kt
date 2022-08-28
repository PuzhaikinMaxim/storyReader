package com.example.storyreader.domain.usecases

import com.example.storyreader.domain.StoryRepository
import com.example.storyreader.domain.models.Story
import javax.inject.Inject

class AddToFavouriteUseCase @Inject constructor(
    private val repository: StoryRepository
) {

    suspend operator fun invoke(story: Story) {
        repository.addInFavourite(story)
    }
}