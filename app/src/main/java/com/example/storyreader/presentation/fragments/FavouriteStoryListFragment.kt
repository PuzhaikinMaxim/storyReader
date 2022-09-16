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
import com.example.storyreader.presentation.viewmodels.FavouriteStoryListViewModel
import java.lang.RuntimeException
import javax.inject.Inject

class FavouriteStoryListFragment: Fragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: FavouriteStoryListViewModel

    private val component by lazy {
        (requireActivity().application as StoryApplication).component
    }

    private lateinit var actionBarActivity: ActionBarActivity

    private var _binding: FragmentStoryListBinding? = null
    private val binding: FragmentStoryListBinding
        get() = _binding ?: throw RuntimeException("Binding is null")

    override fun onAttach(context: Context) {
        component.inject(this)
        if(context is ActionBarActivity) {
            actionBarActivity = context
        }
        requireActivity().setTitle(R.string.favourite_title)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoryListBinding.inflate(inflater, container, false)
        viewModel = viewModelFactory.create(FavouriteStoryListViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBarActivity.setupActionBar(
            binding.toolbar.tbMain,
            ActionBarActivity.FAVOURITE_LIST_FRAGMENT_CODE
        )
        setupStoryList()
    }

    private fun setupStoryList() {
        val adapter = StoryListAdapter()
        with(binding){
            rvStoryList.adapter = adapter
        }
        viewModel.favouriteStoriesList.observe(requireActivity()){
            println(it)
            adapter.storyList = it
        }
        adapter.onLongClickListener = {
            println("cock and balls")
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newInstance(): FavouriteStoryListFragment {
            return FavouriteStoryListFragment()
        }
    }
}