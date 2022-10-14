package com.sultanseidov.watchyou.view.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.sultanseidov.watchyou.view.fragment.DiscoverFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class MovieFragmentFactory  @Inject constructor(
    private val glide : RequestManager
    ) : FragmentFactory() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            DiscoverFragment::class.java.name -> DiscoverFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}