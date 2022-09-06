package com.example.storyreader.di

import android.app.Application
import com.example.storyreader.presentation.fragments.CategoryListFragment
import com.example.storyreader.presentation.fragments.FavouriteStoryListFragment
import com.example.storyreader.presentation.fragments.StoryFragment
import com.example.storyreader.presentation.fragments.StoryListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(storyFragment: StoryFragment)

    fun inject(storyListFragment: StoryListFragment)

    fun inject(categoryListFragment: CategoryListFragment)

    fun inject(favouriteStoryListFragment: FavouriteStoryListFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}