package com.example.storyreader.presentation

import android.app.Application
import com.example.storyreader.di.DaggerApplicationComponent

class StoryApplication: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}