package com.example.storyreader.di

import androidx.lifecycle.ViewModel
import com.example.storyreader.presentation.viewmodels.CategoryListViewModel
import com.example.storyreader.presentation.viewmodels.StoryListViewModel
import com.example.storyreader.presentation.viewmodels.StoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoryListViewModel::class)
    fun bindCategoryListViewModel(viewModel: CategoryListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StoryListViewModel::class)
    fun bindStoryListViewModel(viewModel: StoryListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StoryViewModel::class)
    fun bindStoryViewModel(viewModel: StoryViewModel): ViewModel
}