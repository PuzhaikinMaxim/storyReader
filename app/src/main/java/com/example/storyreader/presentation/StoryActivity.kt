package com.example.storyreader.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.storyreader.R
import com.example.storyreader.databinding.ActivityStoryBinding
import com.example.storyreader.presentation.fragments.StoryFragment
import java.lang.RuntimeException

class StoryActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityStoryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val storyId = parseIntent()
        setupFragmentView(storyId)
    }

    private fun parseIntent(): Int {
        if(!intent.hasExtra(EXTRA_ID)){
            throw RuntimeException("Id parameter is absent")
        }
        return intent.getIntExtra(EXTRA_ID, 1)
    }

    private fun setupFragmentView(id: Int) {
        val fragment = StoryFragment.newInstance(id)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.story_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {

        private const val EXTRA_ID = "id"

        fun newIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, StoryActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }
    }
}