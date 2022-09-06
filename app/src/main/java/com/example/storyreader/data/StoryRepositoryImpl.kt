package com.example.storyreader.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.storyreader.data.localdatabase.StoryDao
import com.example.storyreader.domain.StoryRepository
import com.example.storyreader.domain.models.Category
import com.example.storyreader.domain.models.Story
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val storyDao: StoryDao,
    private val storyMapper: StoryMapper,
    private val categoryMapper: CategoryMapper
): StoryRepository {

    override fun getStoriesList(): LiveData<List<Story>> {
        return Transformations.map(storyDao.getStories()){
            storyMapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun getStoriesByCategory(categoryId: Int): LiveData<List<Story>> {
        return Transformations.map(storyDao.getStoriesOfCategory(categoryId)){
            storyMapper.mapStoriesOfCategory(it)
        }
    }

    override suspend fun getStory(id: Int): Story {
        val storyDbModel = storyDao.getStory(id)
        return storyMapper.mapDbModelToEntity(storyDbModel)
    }

    override fun getCategoriesList(): LiveData<List<Category>> {
        return Transformations.map(storyDao.getCategories()){
            categoryMapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun markRead(id: Int) {
        storyDao.setRead(id)
    }

    override suspend fun addInFavourite(story: Story) {
        storyDao.addInFavourite(
            storyId = story.storyId,
            isFavourite = story.isFavourite
        )
    }

    override suspend fun getFavouriteStories(): LiveData<List<Story>> {
        return Transformations.map(storyDao.getFavouriteStories()){
            storyMapper.mapListDbModelToListEntity(it)
        }
    }
}