package com.challenge.code_base_sdk.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.code_base_sdk.databinding.FragmentItemsBinding
import com.challenge.code_base_sdk.utils.ResultState
import com.challenge.code_base_sdk.viewmodel.MainBaseViewModel
import com.challenge.code_base_sdk.views.adapter.AppAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemsFragment : Fragment() {

    // Declare necessary variables and objects

    private lateinit var appType: String

    private val binding by lazy {
        FragmentItemsBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MainBaseViewModel::class.java]
    }

    private val appAdapter by lazy {
        AppAdapter {
            viewModel.setSelectedItem(it)
            //viewModel.selectedItem = it
            binding.slidingPaneLayout.openPane()
            //Extension function to hide the keyboard when an item is clicked
            binding.slidingPaneLayout.hideInputKeyboard()
            //findNavController().navigate(R.id.action_SearchFragment_to_DetailsFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the app type from the activity intent
        appType = requireActivity().intent.getStringExtra("APP_TYPE") as String

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        setSearchView()

        setRecyclerView()

        searchQuery()

        getCharacters()

        closePane()

        return binding.root
    }

    // Set up the search view behavior
    private fun setSearchView() {
        binding.mainFragment.searchChar.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Call the ViewModel method to search for items matching the query
                newText?.let {
                    viewModel.searchItems(it)
                }
                return false
            }

        })
    }

    // Set up the RecyclerView
    private fun setRecyclerView() {
        binding.mainFragment.charsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = appAdapter
        }
    }


    private fun getCharacters() {
        viewModel.getCharacters(appType)
        lifecycleScope.launch {
            viewModel.characters.collect {
                when (it) {
                    is ResultState.ERROR -> {
                        Snackbar.make(requireView(), "Error", Snackbar.LENGTH_INDEFINITE).setAction("DISMISS"){}.show()
                    }
                    is ResultState.LOADING -> {}
                    is ResultState.SUCCESS -> {
                        appAdapter.updateItems(it.data)
                    }
                }
            }
        }
    }

    // Observe changes to the search query and filter the RecyclerView accordingly
    private fun searchQuery() {
        lifecycleScope.launch {
            viewModel.nextText.observe(requireActivity()) {
                appAdapter.filter(it)
            }
        }
    }

    //Extension function to hide the keyboard when an item is clicked
    private fun View.hideInputKeyboard() {
        val input = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun closePane() {
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (resources.configuration.smallestScreenWidthDp >= 600) {
                    isEnabled = false
                    requireActivity().onBackPressed()
                } else if (binding.slidingPaneLayout.isOpen) {
                    binding.slidingPaneLayout.closePane()
                } else {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        })
    }

}