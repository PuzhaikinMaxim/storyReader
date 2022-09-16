package com.example.storyreader.presentation.viewmodels

import androidx.lifecycle.*
import com.example.storyreader.domain.models.Story
import com.example.storyreader.domain.usecases.AddToFavouriteUseCase
import com.example.storyreader.domain.usecases.GetStoriesListUseCase
import com.example.storyreader.domain.usecases.GetStoryListByCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.RuntimeException
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
    val readCategory = MutableLiveData<ReadCategory>().apply {
        value = ReadCategory.ALL_STORIES
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
                        &&
                        filterReadCategory(it.isRead)
            } ?: listOf<Story>()
        }
        else {
            return storyList.value?.filter {
                filterReadCategory(it.isRead)
            } ?: listOf<Story>()
        }
    }

    private fun filterReadCategory(isRead: Boolean): Boolean {
        return when(readCategory.value) {
            ReadCategory.ALL_STORIES -> true
            ReadCategory.READ_STORIES -> isRead
            ReadCategory.NOT_READ_STORIES -> !isRead
            else -> throw RuntimeException("Category is null")
        }
    }

    fun setStoryListByCategory(categoryId: Int) {
        viewModelScope.launch {
            storyList = getStoryListByCategoryUseCase.invoke(categoryId)
        }
    }

    enum class ReadCategory {
        ALL_STORIES,
        READ_STORIES,
        NOT_READ_STORIES
    }

}