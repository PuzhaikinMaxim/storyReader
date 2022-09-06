package com.example.storyreader.data.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.storyreader.data.localdatabase.models.*
import com.example.storyreader.domain.models.Story

@Dao
interface StoryDao {

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM categories WHERE categoryId = :categoryId")
    fun getStoriesOfCategory(categoryId: Int): LiveData<CategoryWithStories>

    @Query("SELECT * FROM categories")
    fun getCategories(): LiveData<List<CategoryDbModel>>

    /*
    @Query("SELECT * FROM story")
    fun getStories(): LiveData<List<StoryDbModel>>
     */

    @Query("SELECT storyId, storyName, storyName = null, isFavourite, isRead FROM story")
    fun getStories(): LiveData<List<StoryDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCategory(category: CategoryDbModel)

    @Query("UPDATE story SET isFavourite = :isFavourite WHERE storyId = :storyId")
    suspend fun addInFavourite(isFavourite: Boolean, storyId: Int)

    @Query("UPDATE story SET isRead = 1 WHERE storyId = :storyId")
    suspend fun setRead(storyId: Int)

    @Query("SELECT * FROM story s JOIN storyText t ON s.storyId = t.storyId WHERE s.storyId = :storyId")
    suspend fun getStory(storyId: Int): StoryText

    @Query("SELECT storyId, storyName, null, isFavourite, isRead FROM story WHERE isFavourite = 1")
    fun getFavouriteStories(): LiveData<List<StoryDbModel>>
}