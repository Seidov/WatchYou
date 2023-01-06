package com.sultanseidov.watchyou.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.databinding.FragmentDetailsCastBinding
import com.sultanseidov.watchyou.databinding.FragmentDetailsImagesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsImagesFragment:Fragment(R.layout.fragment_details_images) {

    private var _binding: FragmentDetailsImagesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsImagesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
