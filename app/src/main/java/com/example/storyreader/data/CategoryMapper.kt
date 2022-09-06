package com.example.storyreader.data

import com.example.storyreader.data.localdatabase.models.CategoryDbModel
import com.example.storyreader.domain.models.Category
import javax.inject.Inject

class CategoryMapper @Inject constructor() {

    fun mapDbModelToEntity(categoryDbModel: CategoryDbModel): Category {
        return Category(
            categoryId = categoryDbModel.categoryId,
            categoryName = categoryDbModel.categoryName
        )
    }

    fun mapListDbModelToListEntity(list: List<CategoryDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}