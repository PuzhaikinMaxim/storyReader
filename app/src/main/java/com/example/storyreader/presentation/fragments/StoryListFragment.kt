package com.example.storyreader.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.storyreader.R
import com.example.storyreader.databinding.FragmentStoryListBinding
import com.example.storyreader.presentation.*
import com.example.storyreader.presentation.viewmodels.StoryListViewModel
import com.google.android.material.tabs.TabLayout
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

    private lateinit var adapter: StoryListAdapter

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
        binding.toolbar.tbMain.setOnMenuItemClickListener {
            true
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        launchRightMode()
        setupStoryList()
        setupStorySearch()
        setupSortToggle()
        setupSortTabs()
    }

    private fun setupStoryList() {
        adapter = StoryListAdapter()
        with(binding){
            rvStoryList.adapter = adapter
        }
        viewModel.storyList.observe(requireActivity()){
            println(it)
            adapter.storyList = viewModel.filterStoryList()
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
        viewModel.searchFilterText.observe(requireActivity()){
            adapter.storyList = viewModel.filterStoryList()
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
        binding.toolbar.tbMain.setTitle(R.string.all_stories_title)
        actionBarActivity.setupActionBar(
            binding.toolbar.tbMain,
            ActionBarActivity.STORY_LIST_FRAGMENT_CODE
        )
        //
        viewModel.setStoryList()
    }

    private fun launchCategoryStoryMode() {
        actionBarActivity.setupActionBar(
            binding.toolbar.tbMain,
            ActionBarActivity.CATEGORY_LIST_FRAGMENT_CODE
        )
        binding.toolbar.tbMain.inflateMenu(R.menu.toolbar_story_list_menu)
        viewModel.setStoryListByCategory(categoryId)
    }

    private fun setupStorySearch() {
        val searchItem = binding.toolbar.tbMain.menu.getItem(0)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchFilterText.value = newText
                return false
            }

        })
    }

    private fun setupSortToggle() {
        val toggleItem = binding.toolbar.tbMain.menu.getItem(1)
        toggleItem.setOnMenuItemClickListener {
            if(binding.toolbar.llHideMenu.visibility == View.GONE){
                binding.toolbar.llHideMenu.visibility = View.VISIBLE
            }
            else{
                binding.toolbar.llHideMenu.visibility = View.GONE
            }
            true
        }
    }

    private fun setupSortTabs() {
        viewModel.readCategory.observe(requireActivity()){
            adapter.storyList = viewModel.filterStoryList()
        }
        binding.toolbar.tlReadCategories.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> viewModel.readCategory.value = StoryListViewModel.ReadCategory.ALL_STORIES
                    1 -> viewModel.readCategory.value = StoryListViewModel.ReadCategory.READ_STORIES
                    2 -> viewModel.readCategory.value = StoryListViewModel.ReadCategory.NOT_READ_STORIES
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
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