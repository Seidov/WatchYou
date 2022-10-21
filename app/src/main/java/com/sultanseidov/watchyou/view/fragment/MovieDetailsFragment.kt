package com.sultanseidov.watchyou.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.databinding.FragmentDiscoverBinding
import com.sultanseidov.watchyou.databinding.FragmentMovieDetailsBinding
import com.sultanseidov.watchyou.view.viewmodel.MovieViewModel

class MovieDetailsFragment:Fragment(R.layout.fragment_movie_details) {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var movieId="null"
        movieId= arguments?.getString("movieId").toString()

        if (movieId != "null"){

            Toast.makeText(requireContext(), ""+movieId, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}