package com.challenge.code_base_sdk.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.challenge.code_base_sdk.R
import com.challenge.code_base_sdk.databinding.FragmentDetailsBinding
import com.challenge.code_base_sdk.viewmodel.MainBaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MainBaseViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment getDetails()

          getDetails()
        return binding.root
    }

    /**
     * Fetch details of the selected character using a coroutine in the view model.
     */
    private fun getDetails() {
        lifecycleScope.launch {
            viewModel.selectedItem.collect{
                // Set the name of the character in the UI
                binding.nameCharacter.text = it?.name ?: "Character Name"
                // Load the image of the character using Glide and set it in the UI
                Glide.with(requireContext())
                    .load(it?.image ?: "No data")
                    .thumbnail(Glide.with(requireContext()).load(R.drawable.walking).transform(RoundedCorners(160)))
                    .into(binding.imgCharacter)
                // Set the description of the character in the UI
                binding.descriptionCharacter.text = it?.description ?: "Character Description"

            }
        }
    }





}