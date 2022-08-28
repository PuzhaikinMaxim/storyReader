package com.example.storyreader.domain.usecases

import androidx.lifecycle.LiveData
import com.example.storyreader.domain.StoryRepository
import com.example.storyreader.domain.models.Category
import javax.inject.Inject

class GetCategoriesListUseCase @Inject constructor(
    private val repository: StoryRepository
) {

    operator fun invoke(): LiveData<List<Category>> {
        return repository.getCategoriesList()
    }
}