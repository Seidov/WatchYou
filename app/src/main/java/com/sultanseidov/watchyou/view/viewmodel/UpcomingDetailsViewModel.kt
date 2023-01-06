package com.sultanseidov.watchyou.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultanseidov.watchyou.data.entities.base.Resource
import com.sultanseidov.watchyou.data.entities.responceModel.ResponseMovieDetailsModel
import com.sultanseidov.watchyou.data.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingDetailsViewModel @Inject constructor(
    private val repository: IMovieRepository
) : ViewModel() {

    private val movieDetails = MutableLiveData<Resource<ResponseMovieDetailsModel>>()
    val movieDetailsData: LiveData<Resource<ResponseMovieDetailsModel>>
        get() = movieDetails

    fun fetchMovieDetails(id:String) {
        movieDetails.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.getMovieDetails(id)
            movieDetails.value = response
        }
    }
}