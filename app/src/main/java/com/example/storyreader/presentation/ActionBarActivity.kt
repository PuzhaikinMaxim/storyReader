package com.example.storyreader.presentation

import androidx.appcompat.widget.Toolbar

interface ActionBarActivity {

    fun setupActionBar(toolbar: Toolbar, fragmentCode: Int)

    fun setupActionBarWithoutMenu(toolbar: Toolbar)

    companion object {
        const val STORY_LIST_FRAGMENT_CODE = 100
        const val CATEGORY_LIST_FRAGMENT_CODE = 101
        const val FAVOURITE_LIST_FRAGMENT_CODE = 102
    }
}