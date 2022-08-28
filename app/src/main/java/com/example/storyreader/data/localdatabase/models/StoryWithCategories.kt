package com.example.storyreader.data.localdatabase.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.storyreader.domain.models.Story

data class StoryWithCategories(
    @Embedded val story: StoryDbModel,
    @Relation(
        parentColumn = "storyId",
        entity = CategoryDbModel::class,
        entityColumn = "categoryId",
        associateBy = Junction(StoryCategoryCrossRef::class)
    )
    val stories: List<CategoryDbModel>
) {
}