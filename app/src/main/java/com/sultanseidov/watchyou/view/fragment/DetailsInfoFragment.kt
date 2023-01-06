package com.sultanseidov.watchyou.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.RequestManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.data.entities.DiscoverType
import com.sultanseidov.watchyou.data.entities.base.Status
import com.sultanseidov.watchyou.data.entities.moviedateils.Genre
import com.sultanseidov.watchyou.databinding.FragmentDetailsInfoBinding
import com.sultanseidov.watchyou.view.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsInfoFragment:Fragment(R.layout.fragment_details_info) {

    @Inject
    lateinit var  glide: RequestManager

    var movieId = "null"
    var tvId = "null"

    private var _binding: FragmentDetailsInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getSelectedItem()?.let {
            when (it.data?.itemType) {

                DiscoverType.MOVIES -> {
                    viewModel.fetchMovieDetails(movieId)
                }
                DiscoverType.SERIALS -> {}
                else -> {}
            }

        }

        subscribeToObservers()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun subscribeToObservers() {
        viewModel.movieDetailsData.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let {
                        Log.e("movieDetailsData", "SUCCESS")

                        binding.textViewOverview.text=it.overview
                        setupChipGroupDynamically(result.data.genres)

                    }
                }

                Status.ERROR -> {
                    result.message?.let {
                        Log.e("movieDetailsData", "ERROR")
                    }
                }

                Status.LOADING -> {
                    Log.e("movieDetailsData", "LOADING")
                }
            }
        }

    }

    private fun setupChipGroupDynamically(genres: List<Genre>) {
        val chipGroup = ChipGroup(requireContext())
        chipGroup.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        chipGroup.isSingleSelection = true
        chipGroup.isSingleLine = false

        genres.forEach { item ->
            chipGroup.addView(createChip(item.name))
        }

        binding.chipGroupGenres.addView(chipGroup)

    }


    private fun createChip(label: String): Chip {
        val chip = Chip(requireContext(), null, com.google.android.material.R.style.Widget_MaterialComponents_Chip_Entry)
        chip.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        chip.text = label
        chip.isCloseIconVisible = true
        chip.isChipIconVisible = true
        chip.isCheckable = true
        chip.isClickable = true
        //chip.setOnCloseIconClickListener { Toast.makeText(requireContext(), "Chip deleted successfully", Toast.LENGTH_SHORT).show() }
        return chip
    }

}
