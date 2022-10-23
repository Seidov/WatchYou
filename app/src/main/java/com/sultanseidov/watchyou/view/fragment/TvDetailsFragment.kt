package com.sultanseidov.watchyou.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.data.entities.base.Status
import com.sultanseidov.watchyou.databinding.FragmentMovieDetailsBinding
import com.sultanseidov.watchyou.databinding.FragmentTvDetailsBinding
import com.sultanseidov.watchyou.view.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvDetailsFragment:Fragment(R.layout.fragment_tv_details) {

    private var _binding: FragmentTvDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tvId="null"
        tvId= arguments?.getString("tvId").toString()

        if (tvId != "null"){

            viewModel.fetchTvDetails(tvId)

            subscribeToObservers()        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeToObservers() {
        viewModel.tvDetailsData.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let {
                        Log.e("viewModel.tvDetailsData", "SUCCESS")
                        Toast.makeText(requireContext(), ""+it.name, Toast.LENGTH_SHORT).show()
                    }
                }

                Status.ERROR -> {
                    result.message?.let {
                        Log.e("viewModel.tvDetailsData", "ERROR: "+result.message)
                    }
                }

                Status.LOADING -> {
                    Log.e("viewModel.tvDetailsData", "LOADING")
                }
            }
        }

    }

}