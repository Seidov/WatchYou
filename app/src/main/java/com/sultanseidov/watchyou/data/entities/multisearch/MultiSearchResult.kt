package com.sultanseidov.watchyou.data.entities.multisearch

import com.sultanseidov.watchyou.data.entities.tvshow.TvShowModel
import com.sultanseidov.watchyou.data.entities.movie.MovieModel

data class MultiSearchResult(val moviesList: List<MovieModel>?=null,val tvShowsList: List<TvShowModel>?=null) {
}