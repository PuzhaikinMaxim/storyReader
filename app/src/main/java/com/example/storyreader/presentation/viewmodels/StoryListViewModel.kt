package com.example.storyreader.presentation.viewmodels

import androidx.lifecycle.*
import com.example.storyreader.domain.StoryRepository
import com.example.storyreader.domain.models.Story
import com.example.storyreader.domain.usecases.AddToFavouriteUseCase
import com.example.storyreader.domain.usecases.GetStoriesListUseCase
import com.example.storyreader.domain.usecases.GetStoryListByCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoryListViewModel @Inject constructor(
    private val getStoryListUseCase: GetStoriesListUseCase,
    private val addToFavouriteUseCase: AddToFavouriteUseCase,
    private val getStoryListByCategoryUseCase: GetStoryListByCategoryUseCase
): ViewModel() {

    lateinit var storyList: LiveData<List<Story>>
    val searchFilterText = MutableLiveData<String>().apply {
        value = ""
    }

    fun addStoryInFavourite(story: Story) {
        viewModelScope.launch(Dispatchers.Default) {
            addToFavouriteUseCase.invoke(story.copy(isFavourite = !story.isFavourite))
        }
    }

    fun setStoryList() {
        storyList = getStoryListUseCase.invoke()
    }

    fun filterStoryList(): List<Story> {
        if(searchFilterText.value != "") {
            return storyList.value?.filter {
                it.storyName.lowercase().contains(searchFilterText.value!!.lowercase())
            } ?: listOf<Story>()
        }
        else {
            return storyList.value ?: listOf<Story>()
        }
    }

    fun setStoryListByCategory(categoryId: Int) {
        viewModelScope.launch {
            storyList = getStoryListByCategoryUseCase.invoke(categoryId)
        }
    }

}