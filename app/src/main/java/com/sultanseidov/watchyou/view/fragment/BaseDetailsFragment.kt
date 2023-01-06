package com.sultanseidov.watchyou.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.bumptech.glide.RequestManager
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialSharedAxis
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.data.entities.DiscoverType
import com.sultanseidov.watchyou.data.entities.base.Status
import com.sultanseidov.watchyou.databinding.FragmentBaseDetailsBinding
import com.sultanseidov.watchyou.util.Util
import com.sultanseidov.watchyou.view.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BaseDetailsFragment : Fragment(R.layout.fragment_base_details) {

    @Inject
    lateinit var glide: RequestManager

    private var _binding: FragmentBaseDetailsBinding? = null
    private val binding: FragmentBaseDetailsBinding
        get() = _binding!!


    private lateinit var detailsInfoFragment: DetailsInfoFragment
    private lateinit var detailsCastFragment: DetailsCastFragment
    private lateinit var detailsImagesFragment: DetailsImagesFragment
    private lateinit var detailsViewPagerAdapter: DetailsViewPagerAdapter

    private val viewModel: MovieViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsInfoFragment = DetailsInfoFragment()
        detailsCastFragment = DetailsCastFragment()
        detailsImagesFragment = DetailsImagesFragment()

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            interpolator = FastOutSlowInInterpolator()
            duration = resources.getInteger(R.integer.material_motion_duration_long_1).toLong()
        }
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            interpolator = FastOutSlowInInterpolator()
            duration = resources.getInteger(R.integer.material_motion_duration_long_1).toLong()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBaseDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getSelectedItem()?.let {
            when (it.data?.itemType) {

                DiscoverType.MOVIES -> {

                    viewModel.fetchMovieDetails(it.data.itemId)
                    initializeViewPager()
                    subscribeToObservers()
                }
                DiscoverType.SERIALS -> {}
                else -> {}
            }

        }


        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }


    }

    private fun subscribeToObservers() {

        viewModel.movieDetailsData.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let {
                        Log.e("movieDetailsData", "SUCCESS")
                        glide.load(Util.IMAGE_URL + it.backdrop_path).into(binding.imageView)
                        binding.toolbar.title = it.original_title

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
        viewModel.selectedItem.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let {
                        Log.e("selectedItem", "SUCCESS")

                    }
                }

                Status.ERROR -> {
                    result.message?.let {
                        Log.e("selectedItem", "ERROR")
                    }
                }

                Status.LOADING -> {
                    Log.e("selectedItem", "LOADING")
                }
            }

        }

    }


    private fun initializeViewPager() {
        detailsViewPagerAdapter = DetailsViewPagerAdapter(childFragmentManager, lifecycle)
        detailsViewPagerAdapter.addFragment(detailsInfoFragment)
        detailsViewPagerAdapter.addFragment(detailsCastFragment)
        detailsViewPagerAdapter.addFragment(detailsImagesFragment)
        binding.apply {
            viewPager.adapter = detailsViewPagerAdapter
            viewPager.isUserInputEnabled = true
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Info"
                    1 -> tab.text = "Cast"
                    2 -> tab.text = "Images"
                }
            }.attach()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}