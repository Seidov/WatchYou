package com.sultanseidov.watchyou.data.remote

import com.sultanseidov.watchyou.data.entities.responceModel.*
import com.sultanseidov.watchyou.data.entities.responceModel.ResponseMultiSearchModel
import com.sultanseidov.watchyou.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ITMDBApi {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") api_key:String=API_KEY,
        @Query("language") language:String="en-US",
        @Query("page") page:String="1"
    ): Response<ResponseUpcomingMovieModel>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") api_key:String=API_KEY,
        @Query("language") language:String="en-US",
        @Query("page") page:String="1"
    ):Response<ResponseTopRatedMovieModel>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key:String=API_KEY,
        @Query("language") language:String="en-US",
        @Query("page") page:String="1"
    ):Response<ResponsePopularMovieModel>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("api_key") api_key:String=API_KEY,
        @Query("language") language:String="en-US",
        @Query("page") page:String="1"
    ):Response<ResponseTopRatedTvShowModel>

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") api_key:String=API_KEY,
        @Query("language") language:String="en-US",
        @Query("page") page:String="1"
    ):Response<ResponsePopularTvShowModel>

    @GET("search/multi")
    suspend fun getMultiSearch(
        @Query("query") query:String,
        @Query("api_key") api_key:String=API_KEY,
        @Query("language") language:String="en-US"
    ):Response<ResponseMultiSearchModel>



}