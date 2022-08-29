package com.example.storyreader.data

import com.example.storyreader.data.localdatabase.models.CategoryDbModel
import com.example.storyreader.data.localdatabase.models.CategoryWithStories
import com.example.storyreader.data.localdatabase.models.StoryDbModel
import com.example.storyreader.domain.models.Category
import com.example.storyreader.domain.models.Story
import javax.inject.Inject

class StoryMapper @Inject constructor() {

    fun mapDbModelToEntity(storyDbModel: StoryDbModel): Story {
        return Story(
            storyId = storyDbModel.storyId,
            storyName = storyDbModel.storyName,
            storyText = storyDbModel.storyText,
            isFavourite = storyDbModel.isFavourite,
            isRead = storyDbModel.isRead
        )
    }

    fun mapStoriesOfCategory(categoryWithStories: CategoryWithStories) = mapListDbModelToListEntity(categoryWithStories.stories)

    fun mapListDbModelToListEntity(list: List<StoryDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}