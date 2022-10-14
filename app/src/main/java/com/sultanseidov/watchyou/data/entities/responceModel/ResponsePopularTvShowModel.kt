package com.sultanseidov.watchyou.data.entities.responceModel

import com.sultanseidov.watchyou.data.entities.tvshow.TvShowModel


data class ResponsePopularTvShowModel(
    val page: Int,
    val results: List<TvShowModel>,
    val total_pages: Int,
    val total_results: Int
)