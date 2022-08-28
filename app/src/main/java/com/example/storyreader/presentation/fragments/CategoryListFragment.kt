package com.example.storyreader.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.storyreader.R
import com.example.storyreader.databinding.FragmentCategoryListBinding
import com.example.storyreader.databinding.FragmentStoryBinding
import com.example.storyreader.presentation.CategoryListAdapter
import com.example.storyreader.presentation.StoryApplication
import com.example.storyreader.presentation.ViewModelFactory
import com.example.storyreader.presentation.viewmodels.CategoryListViewModel
import java.lang.RuntimeException
import javax.inject.Inject

class CategoryListFragment: Fragment() {

    private val component by lazy {
        (requireActivity().application as StoryApplication).component
    }

    private lateinit var viewModel: CategoryListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: FragmentCategoryListBinding? = null
    private val binding: FragmentCategoryListBinding
        get() = _binding ?: throw RuntimeException("Binding is not set")

    override fun onAttach(context: Context) {
        component.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.categories_title)
        viewModel = viewModelFactory.create(CategoryListViewModel::class.java)
        setupCategoryList()
    }

    private fun setupCategoryList() {
        val adapter = CategoryListAdapter()
        with(binding){
            rvCategoryList.adapter = adapter
            viewModel.categories.observe(viewLifecycleOwner) {
                adapter.categoryList = it
            }
        }
    }

    companion object {

        fun newInstance(): CategoryListFragment {
            return CategoryListFragment()
        }
    }

}