package com.sultanseidov.watchyou.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultanseidov.watchyou.data.entities.DiscoverType
import com.sultanseidov.watchyou.data.entities.DiscoverViewsModel
import com.sultanseidov.watchyou.data.entities.SelectedItemModel
import com.sultanseidov.watchyou.data.entities.movie.MovieModel
import com.sultanseidov.watchyou.data.entities.base.Resource
import com.sultanseidov.watchyou.data.entities.base.Status
import com.sultanseidov.watchyou.data.entities.multisearch.MultiSearchResult
import com.sultanseidov.watchyou.data.entities.responceModel.*
import com.sultanseidov.watchyou.data.entities.multisearch.Result
import com.sultanseidov.watchyou.data.entities.tvshow.TvShowModel
import com.sultanseidov.watchyou.data.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: IMovieRepository
) : ViewModel() {

    val moviesListFromDB = repository.getMovies()
    val tvShowsListFromDB = repository.getTvShows()

    fun deleteMovie(movie: MovieModel) = viewModelScope.launch {
        repository.deleteMovie(movie)
    }

    fun insertMovie(movie: MovieModel) = viewModelScope.launch {
        repository.insertMovie(movie)
    }

    fun deleteTvShow(tvShowModel: TvShowModel) = viewModelScope.launch {
        repository.deleteTvShow(tvShowModel)
    }

    fun insertTvShow(tvShowModel: TvShowModel) = viewModelScope.launch {
        repository.insertTvShow(tvShowModel)
    }

    private val upcomingMovies = MutableLiveData<Resource<ResponseUpcomingMovieModel>>()
    val upcomingMoviesList: LiveData<Resource<ResponseUpcomingMovieModel>>
        get() = upcomingMovies

    private val topRatedMovies = MutableLiveData<Resource<ResponseTopRatedMovieModel>>()
    val topRatedMoviesList: LiveData<Resource<ResponseTopRatedMovieModel>>
        get() = topRatedMovies

    private var popularMovies = MutableLiveData<Resource<ResponsePopularMovieModel>>()
    val popularMoviesList: LiveData<Resource<ResponsePopularMovieModel>>
        get() = popularMovies

    private val topRatedTvShows = MutableLiveData<Resource<ResponseTopRatedTvShowModel>>()
    val topRatedTvShowsList: LiveData<Resource<ResponseTopRatedTvShowModel>>
        get() = topRatedTvShows

    private val popularTvShows = MutableLiveData<Resource<ResponsePopularTvShowModel>>()
    val popularTvShowsList: LiveData<Resource<ResponsePopularTvShowModel>>
        get() = popularTvShows

    private val multiSearchResult = MutableLiveData<Resource<MultiSearchResult>>()
    val multiSearchResultList: LiveData<Resource<MultiSearchResult>>
        get() = multiSearchResult

    private val homeListResult = MutableLiveData<Resource<ArrayList<DiscoverViewsModel>>>()
    val homeListResultList: LiveData<Resource<ArrayList<DiscoverViewsModel>>>
        get() = homeListResult

    private val movieDetails = MutableLiveData<Resource<ResponseMovieDetailsModel>>()
    val movieDetailsData: LiveData<Resource<ResponseMovieDetailsModel>>
        get() = movieDetails

    private val tvDetails = MutableLiveData<Resource<ResponseTvDetailsModel>>()
    val tvDetailsData: LiveData<Resource<ResponseTvDetailsModel>>
        get() = tvDetails


    private val selectedItemModel = MutableLiveData<Resource<SelectedItemModel>>()
    val selectedItem: LiveData<Resource<SelectedItemModel>>
        get() = selectedItemModel

    fun setSelectedItem(_selectedItemModel: SelectedItemModel){
        selectedItemModel.value=Resource.loading(null)
        viewModelScope.launch {
            selectedItemModel.value= Resource(
                status = Status.SUCCESS,
                data = _selectedItemModel,
                message = "SUCCESS"
            )

        }

    }

    fun getSelectedItem() = selectedItemModel.value



    fun fetchHomeList() {

        homeListResult.value = Resource.loading(null)

        viewModelScope.launch {

            var homeList = arrayListOf<DiscoverViewsModel>(
                DiscoverViewsModel(DiscoverType.STORY).apply {
                    type = DiscoverType.STORY
                    upcomingMoviesList = repository.getUpcomingMovies().data?.results!!
                },
                DiscoverViewsModel(DiscoverType.MOVIES).apply {
                    type = DiscoverType.MOVIES
                    popularMoviesList = repository.getPopularMovies().data?.results!!
                },
                DiscoverViewsModel(DiscoverType.MOVIES).apply {
                    type = DiscoverType.MOVIES
                    topRatedMoviesList = repository.getTopRatedMovies().data?.results!!
                },
                DiscoverViewsModel(DiscoverType.SERIALS).apply {
                    type = DiscoverType.SERIALS
                    popularTvShowsList = repository.getPopularTvShows().data?.results!!
                },
                DiscoverViewsModel(DiscoverType.SERIALS).apply {
                    type = DiscoverType.SERIALS
                    topRatedTvShowsList = repository.getTopRatedTvShows().data?.results!!
                }
            )

            homeListResult.value=Resource(
                status = Status.SUCCESS,
                data = homeList,
                message = "SUCCESS"
            )
        }

    }

    fun fetchUpcomingMovies() {
        upcomingMovies.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.getUpcomingMovies()
            upcomingMovies.value = response
        }
    }

    fun fetchTopRatedMovies() {
        topRatedMovies.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.getTopRatedMovies()
            topRatedMovies.value = response
        }
    }

    fun fetchPopularMovies() {
        popularMovies.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.getPopularMovies()
            popularMovies.value = response
        }
    }

    fun fetchTopRatedTvShows() {
        topRatedTvShows.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.getTopRatedTvShows()
            topRatedTvShows.value = response
        }
    }

    fun fetchPopularTvShows() {
        popularTvShows.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.getPopularTvShows()
            popularTvShows.value = response
        }
    }

    fun fetchMultiSearch(query: String) {
        multiSearchResult.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.getMultiSearch(query)
            if (!response.data?.results.isNullOrEmpty()) {

                val listMovie = response.data?.results?.filter { list ->
                    list.media_type == "movie"
                }
                val listTv = response.data?.results?.filter { list ->
                    list.media_type == "tv"
                }

                try {
                    multiSearchResult.value = Resource(
                        status = Status.SUCCESS,
                        data = MultiSearchResult(
                            listMovie?.convertResultToMovieModel(),
                            listTv?.convertResultToTvShowModel()
                        ),
                        message = "SUCCESS"
                    )

                } catch (e: Exception) {
                    Log.e("Exception", "" + e)
                }
            } else {
                Log.e("fetchMultiSearch", "Data Null!")

            }
        }
    }

    fun fetchMovieDetails(id:String) {
        movieDetails.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.getMovieDetails(id)
            movieDetails.value = response
        }
    }

    fun fetchTvDetails(id:String) {
        tvDetails.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.getTvDetails(id)
            tvDetails.value = response
        }
    }


    private fun List<Result>.convertResultToMovieModel(): List<MovieModel> {
        return this.map {
            MovieModel(
                it.id,
                it.adult,
                it.backdrop_path,
                it.original_language,
                it.original_title,
                it.overview,
                it.popularity,
                it.poster_path,
                it.release_date,
                it.title,
                it.video,
                it.vote_average,
                it.vote_count
            )
        }
    }

    private fun List<Result>.convertResultToTvShowModel(): List<TvShowModel> {
        return this.map {
            TvShowModel(
                it.id,
                it.backdrop_path,
                it.first_air_date,
                it.name,
                it.original_language,
                it.original_name,
                it.overview,
                it.popularity,
                it.poster_path,
                it.vote_average,
                it.vote_count
            )
        }
    }

}