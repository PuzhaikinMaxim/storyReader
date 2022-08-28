package com.example.storyreader.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.storyreader.R
import com.example.storyreader.databinding.FragmentStoryListBinding
import com.example.storyreader.presentation.StoryActivity
import com.example.storyreader.presentation.StoryApplication
import com.example.storyreader.presentation.StoryListAdapter
import com.example.storyreader.presentation.ViewModelFactory
import com.example.storyreader.presentation.viewmodels.StoryListViewModel
import javax.inject.Inject

class StoryListFragment: Fragment() {

    private lateinit var viewModel: StoryListViewModel

    private val component by lazy {
        (requireActivity().application as StoryApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: FragmentStoryListBinding? = null
    private val binding: FragmentStoryListBinding
        get() = _binding ?: throw RuntimeException("Binding is not set")

    override fun onAttach(context: Context) {
        component.inject(this)
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
        setupStoryList()
        requireActivity().setTitle(R.string.all_stories_title)
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
            viewModel.addStoryInFavourite(it.copy(isFavourite = !it.isFavourite))
        }
        adapter.onItemClickedListener = {
            val intent = StoryActivity.newIntent(requireContext(), it.storyId)
            requireActivity().startActivity(intent)
        }
    }

    private fun launchRightMode() {

    }

    companion object {

        private const val SCREEN_MODE = "screen_mode"
        private const val MODE_ALL_STORIES = "all_stories"
        private const val MODE_CATEGORY_STORY = "category_story"
        private const val CATEGORY_ID = "category_id"

        fun newInstance(): StoryListFragment {
            return StoryListFragment()
        }
    }
}