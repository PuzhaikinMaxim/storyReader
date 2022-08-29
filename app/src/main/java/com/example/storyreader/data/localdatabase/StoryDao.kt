package com.example.storyreader.data.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.storyreader.data.localdatabase.models.CategoryDbModel
import com.example.storyreader.data.localdatabase.models.CategoryWithStories
import com.example.storyreader.data.localdatabase.models.StoryDbModel
import com.example.storyreader.data.localdatabase.models.StoryWithCategories
import com.example.storyreader.domain.models.Story

@Dao
interface StoryDao {

    @Query("SELECT * FROM categories WHERE categoryId = :categoryId")
    fun getStoriesOfCategory(categoryId: Int): LiveData<CategoryWithStories>

    @Query("SELECT * FROM categories")
    fun getCategories(): LiveData<List<CategoryDbModel>>

    /*
    @Query("SELECT * FROM story")
    fun getStories(): LiveData<List<StoryDbModel>>

     */

    @Query("SELECT storyId, storyName, null, isFavourite, isRead FROM story")
    fun getStories(): LiveData<List<StoryDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCategory(category: CategoryDbModel)

    @Query("UPDATE story SET isFavourite = :isFavourite WHERE storyId = :storyId")
    suspend fun addInFavourite(isFavourite: Boolean, storyId: Int)

    @Query("UPDATE story SET isRead = 1 WHERE storyId = :storyId")
    suspend fun setRead(storyId: Int)

    @Query("SELECT * FROM story WHERE storyId = :storyId")
    suspend fun getStory(storyId: Int): StoryDbModel
}