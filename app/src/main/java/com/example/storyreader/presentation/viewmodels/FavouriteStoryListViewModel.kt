package com.example.storyreader.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyreader.domain.models.Story
import com.example.storyreader.domain.usecases.AddToFavouriteUseCase
import com.example.storyreader.domain.usecases.GetFavouriteStoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import javax.inject.Inject

class FavouriteStoryListViewModel @Inject constructor(
    private val getFavouriteStoriesUseCase: GetFavouriteStoriesUseCase,
    private val addToFavouriteUseCase: AddToFavouriteUseCase
) : ViewModel() {

    val favouriteStoriesList by lazy {
        var stories: LiveData<List<Story>>? = null
        viewModelScope.launch {
            stories = getFavouriteStoriesUseCase.invoke()
        }
        stories ?: throw RuntimeException("Live data is null")
    }

    fun addStoryInFavourite(story: Story) {
        viewModelScope.launch(Dispatchers.Default) {
            println(story.copy(isFavourite = !story.isFavourite))
            addToFavouriteUseCase.invoke(story.copy(isFavourite = !story.isFavourite))
        }
    }
}