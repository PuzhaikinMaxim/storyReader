package com.example.storyreader.data.localdatabase.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.storyreader.domain.models.Story

data class CategoryWithStories(
    @Embedded val category: CategoryDbModel,
    @Relation(
        parentColumn = "categoryId",
        entity = StoryDbModel::class,
        entityColumn = "storyId",
        associateBy = Junction(StoryCategoryCrossRef::class)
    )
    val stories: List<StoryDbModel>
)