package com.sultanseidov.watchyou.view.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.sultanseidov.watchyou.view.activity.UpcomingDetailsActivity
import com.sultanseidov.watchyou.view.fragment.BaseDetailsFragment
import com.sultanseidov.watchyou.view.fragment.DiscoverFragment
import com.sultanseidov.watchyou.view.fragment.MovieDetailsFragment
import com.sultanseidov.watchyou.view.fragment.TvDetailsFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class MovieFragmentFactory  @Inject constructor(
    private val glide : RequestManager
    ) : FragmentFactory() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            DiscoverFragment::class.java.name -> DiscoverFragment()
            MovieDetailsFragment::class.java.name -> MovieDetailsFragment()
            TvDetailsFragment::class.java.name -> TvDetailsFragment()
            BaseDetailsFragment::class.java.name -> BaseDetailsFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}