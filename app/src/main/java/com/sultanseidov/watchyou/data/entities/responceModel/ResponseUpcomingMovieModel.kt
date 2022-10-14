package com.sultanseidov.watchyou.data.entities.responceModel

import com.sultanseidov.watchyou.data.entities.movie.MovieModel
import com.sultanseidov.watchyou.data.entities.movie.Dates

data class ResponseUpcomingMovieModel(
    val dates: Dates,
    val page: Int,
    val results: List<MovieModel>,
    val total_pages: Int,
    val total_results: Int
)