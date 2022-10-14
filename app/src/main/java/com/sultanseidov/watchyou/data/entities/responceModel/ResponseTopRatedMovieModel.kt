package com.sultanseidov.watchyou.data.entities.responceModel

import com.sultanseidov.watchyou.data.entities.movie.MovieModel

data class ResponseTopRatedMovieModel(
    val page: Int,
    val results: List<MovieModel>,
    val total_pages: Int,
    val total_results: Int
)