package com.example.storyreader.domain.usecases

import com.example.storyreader.domain.StoryRepository
import javax.inject.Inject

class SetReadUseCase @Inject constructor(private val repository: StoryRepository) {

    suspend operator fun invoke(id: Int) {
        repository.markRead(id)
    }
}