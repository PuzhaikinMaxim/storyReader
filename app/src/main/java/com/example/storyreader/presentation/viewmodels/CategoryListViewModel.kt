package com.example.storyreader.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.storyreader.domain.usecases.GetCategoriesListUseCase
import javax.inject.Inject

class CategoryListViewModel @Inject constructor(
    private val getCategoriesListUseCase: GetCategoriesListUseCase
): ViewModel() {

    val categories = getCategoriesListUseCase.invoke()
}