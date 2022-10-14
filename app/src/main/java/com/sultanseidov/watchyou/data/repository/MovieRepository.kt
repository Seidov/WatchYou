package com.sultanseidov.watchyou.data.repository

import androidx.lifecycle.LiveData
import com.sultanseidov.watchyou.data.entities.movie.MovieModel
import com.sultanseidov.watchyou.data.entities.base.Resource
import com.sultanseidov.watchyou.data.entities.responceModel.*
import com.sultanseidov.watchyou.data.entities.responceModel.ResponseMultiSearchModel
import com.sultanseidov.watchyou.data.entities.tvshow.TvShowModel
import com.sultanseidov.watchyou.data.local.MovieDao
import com.sultanseidov.watchyou.data.local.TvShowDao
import com.sultanseidov.watchyou.data.remote.ITMDBApi
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao,
    private val retrofitApi: ITMDBApi
) : IMovieRepository {

    override fun getMovies(): LiveData<List<MovieModel>> {
        return movieDao.observeMovies()
    }

    override fun getTvShows(): LiveData<List<TvShowModel>> {
        return tvShowDao.observeTvShows()
    }

    override suspend fun insertMovie(movieModel: MovieModel) {
        movieDao.insertMovie(movieModel)
    }

    override suspend fun deleteMovie(movieModel: MovieModel) {
        movieDao.deleteMovie(movieModel)
    }

    override suspend fun insertTvShow(tvShowModel: TvShowModel) {
        tvShowDao.insertTvShow(tvShowModel)
    }

    override suspend fun deleteTvShow(tvShowModel: TvShowModel) {
        tvShowDao.deleteTvShow(tvShowModel)
    }

    override suspend fun getUpcomingMovies(): Resource<ResponseUpcomingMovieModel> {
        return try {
            val response = retrofitApi.getUpcomingMovies()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }

    override suspend fun getTopRatedMovies(): Resource<ResponseTopRatedMovieModel> {
        return try {
            val response = retrofitApi.getTopRatedMovies()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }

    override suspend fun getPopularMovies(): Resource<ResponsePopularMovieModel> {
        return try {
            val response = retrofitApi.getPopularMovies()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }

    override suspend fun getTopRatedTvShows(): Resource<ResponseTopRatedTvShowModel> {
        return try {
            val response = retrofitApi.getTopRatedTvShows()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }

    override suspend fun getPopularTvShows(): Resource<ResponsePopularTvShowModel> {
        return try {
            val response = retrofitApi.getPopularTvShows()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }

    override suspend fun getMultiSearch(query:String): Resource<ResponseMultiSearchModel> {
        return try {
            val response = retrofitApi.getMultiSearch(query)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }
}