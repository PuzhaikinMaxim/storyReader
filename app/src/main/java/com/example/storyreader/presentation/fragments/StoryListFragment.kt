package com.example.storyreader.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.storyreader.R
import com.example.storyreader.databinding.FragmentStoryListBinding
import com.example.storyreader.presentation.*
import com.example.storyreader.presentation.viewmodels.StoryListViewModel
import java.lang.IllegalStateException
import javax.inject.Inject

class StoryListFragment: Fragment() {

    private lateinit var viewModel: StoryListViewModel

    private var screenMode = MODE_ALL_STORIES

    private var categoryId = DEFAULT_ID

    private val component by lazy {
        (requireActivity().application as StoryApplication).component
    }

    private lateinit var actionBarActivity: ActionBarActivity

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: FragmentStoryListBinding? = null
    private val binding: FragmentStoryListBinding
        get() = _binding ?: throw RuntimeException("Binding is not set")

    override fun onAttach(context: Context) {
        component.inject(this)
        if(context is ActionBarActivity){
            actionBarActivity = context
        }
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryListBinding.inflate(inflater, container, false)
        viewModel = viewModelFactory.create(StoryListViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        launchRightMode()
        setupStoryList()
    }

    private fun setupStoryList() {
        val adapter = StoryListAdapter()
        with(binding){
            rvStoryList.adapter = adapter
        }
        viewModel.storyList.observe(requireActivity()){
            println(it)
            adapter.storyList = it
        }
        adapter.onLongClickListener = {
            viewModel.addStoryInFavourite(it)
        }
        adapter.onItemClickedListener = {
            //val intent = StoryActivity.newIntent(requireContext(), it.storyId)
            //requireActivity().startActivity(intent)
            val fragment = StoryFragment.newInstance(it.storyId)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun parseParams() {
        try {
            val args = requireArguments()
            if(!args.containsKey(SCREEN_MODE)) {
                throw RuntimeException("Screen mode don't set")
            }
            screenMode = args.getString(SCREEN_MODE) ?: throw RuntimeException("Screen mode don't set")
            if(screenMode == MODE_CATEGORY_STORY) {
                if(!args.containsKey(CATEGORY_ID)) {
                    throw RuntimeException("Id does not set")
                }
                categoryId = args.getInt(CATEGORY_ID, DEFAULT_ID)
            }
        }
        catch (e: IllegalStateException) {
            launchAllStoriesMode()
        }
    }

    private fun launchRightMode() {
        when(screenMode){
            MODE_ALL_STORIES -> launchAllStoriesMode()
            MODE_CATEGORY_STORY -> launchCategoryStoryMode()
            MODE_DEFAULT -> throw RuntimeException("Mode is not set")
            else -> throw RuntimeException("Unknown mode")
        }
    }

    private fun launchAllStoriesMode() {
        requireActivity().setTitle(R.string.all_stories_title)
        actionBarActivity.setupActionBar(
            binding.toolbar.root,
            ActionBarActivity.STORY_LIST_FRAGMENT_CODE
        )
        viewModel.setStoryList()
    }

    private fun launchCategoryStoryMode() {
        actionBarActivity.setupActionBar(
            binding.toolbar.root,
            ActionBarActivity.CATEGORY_LIST_FRAGMENT_CODE
        )
        viewModel.setStoryListByCategory(categoryId)
    }

    companion object {

        private const val SCREEN_MODE = "screen_mode"
        private const val MODE_DEFAULT = "not set"
        private const val MODE_ALL_STORIES = "all_stories"
        private const val MODE_CATEGORY_STORY = "category_story"
        private const val CATEGORY_ID = "category_id"
        private const val DEFAULT_ID = -1

        fun newInstance(): StoryListFragment {
            return StoryListFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ALL_STORIES)
                }
            }
        }

        fun newInstance(categoryId: Int): StoryListFragment {
            return StoryListFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_CATEGORY_STORY)
                    putInt(CATEGORY_ID, categoryId)
                }
            }
        }
    }
}