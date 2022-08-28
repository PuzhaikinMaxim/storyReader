package com.example.storyreader.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyreader.domain.models.Story
import com.example.storyreader.domain.usecases.GetStoryUseCase
import com.example.storyreader.domain.usecases.SetReadUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoryViewModel @Inject constructor(
    private val getStoryUseCase: GetStoryUseCase,
    private val setReadUseCase: SetReadUseCase
): ViewModel() {

    private val _story = MutableLiveData<Story>()
    val story: LiveData<Story>
        get() = _story

    fun getStory(id: Int) {
        viewModelScope.launch {
            _story.value = getStoryUseCase.invoke(id)
            print(_story.value)
        }
    }

    fun setRead(id: Int) {
        viewModelScope.launch {
            setReadUseCase.invoke(id)
        }
    }
}