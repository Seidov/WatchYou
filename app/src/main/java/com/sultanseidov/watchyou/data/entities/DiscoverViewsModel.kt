package com.sultanseidov.watchyou.data.entities

import com.sultanseidov.watchyou.data.entities.movie.MovieModel
import com.sultanseidov.watchyou.data.entities.responceModel.ResponsePopularMovieModel
import com.sultanseidov.watchyou.data.entities.responceModel.ResponseTopRatedTvShowModel
import com.sultanseidov.watchyou.data.entities.tvshow.TvShowModel

class DiscoverViewsModel(
    var type: DiscoverType,
    var upcomingMoviesList: List<MovieModel>?=null,
    var popularMoviesList: List<MovieModel>?=null,
    var topRatedMoviesList: List<MovieModel>?=null,
    var popularTvShowsList: List<TvShowModel>?=null,
    var topRatedTvShowsList: List<TvShowModel>?=null
) {
}