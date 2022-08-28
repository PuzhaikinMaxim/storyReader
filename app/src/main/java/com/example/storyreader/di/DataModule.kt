package com.example.storyreader.di

import android.app.Application
import com.example.storyreader.data.StoryRepositoryImpl
import com.example.storyreader.data.localdatabase.AppDatabase
import com.example.storyreader.data.localdatabase.StoryDao
import com.example.storyreader.domain.StoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindStoryRepository(impl: StoryRepositoryImpl): StoryRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideStoryDao(
            application: Application
        ): StoryDao {
            return AppDatabase.getInstance(application).storyDao()
        }
    }
}