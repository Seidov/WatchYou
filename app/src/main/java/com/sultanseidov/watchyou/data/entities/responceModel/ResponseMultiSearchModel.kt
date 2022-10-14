package com.sultanseidov.watchyou.data.entities.responceModel

import com.sultanseidov.watchyou.data.entities.multisearch.Result

data class ResponseMultiSearchModel(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)