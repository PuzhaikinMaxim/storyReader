package com.example.storyreader.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.storyreader.databinding.FragmentStoryBinding
import com.example.storyreader.presentation.StoryApplication
import com.example.storyreader.presentation.ViewModelFactory
import com.example.storyreader.presentation.viewmodels.StoryViewModel
import java.lang.RuntimeException
import javax.inject.Inject

class StoryFragment: Fragment() {

    private var _binding: FragmentStoryBinding? = null
    private val binding: FragmentStoryBinding
        get() = _binding ?: throw RuntimeException("Binding is not set")

    private var storyId: Int = DEFAULT_ID

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: StoryViewModel

    private val component by lazy {
        (requireActivity().application as StoryApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModelFactory.create(StoryViewModel::class.java)
        viewModel.setRead(storyId)
        parseIntent()
        setStoryText()
    }

    private fun parseIntent() {
        val args = requireArguments()
        if(!args.containsKey(BUNDLE_ID)){
            throw RuntimeException("Id does not set")
        }
        storyId = args.getInt(BUNDLE_ID, DEFAULT_ID)
    }

    private fun setStoryText() {
        viewModel.getStory(storyId)
        viewModel.story.observe(requireActivity()){
            if(it == null) return@observe
            print(it)
            binding.tvStoryName.text = it.storyName
            binding.tvStoryText.text = it.storyText
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val BUNDLE_ID = "id"
        private const val DEFAULT_ID = -1

        fun newInstance(id: Int): StoryFragment {
            val fragment = StoryFragment()
            fragment.apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_ID, id)
                }
            }
            return fragment
        }
    }
}