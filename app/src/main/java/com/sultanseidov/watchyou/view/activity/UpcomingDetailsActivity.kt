package com.sultanseidov.watchyou.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.data.entities.base.Status
import com.sultanseidov.watchyou.view.viewmodel.UpcomingDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UpcomingDetailsActivity : AppCompatActivity() {
    //private val viewModel by viewModels<UpcomingDetailsViewModel>()
    private val viewModel: UpcomingDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_details)

        var bundle :Bundle ?=intent.extras
        var movieId = bundle!!.getString("movieId")

        //Toast.makeText(this, ""+movieId, Toast.LENGTH_SHORT).show()

        if (movieId != null) {

            viewModel.fetchMovieDetails(movieId)

        }

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.movieDetailsData.observe(this){ result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let {
                        Log.e("movieDetailsData", "SUCCESS")
                    }
                }

                Status.ERROR -> {
                    result.message?.let {
                        Log.e("movieDetailsData", "ERROR")
                    }
                }

                Status.LOADING -> {
                    Log.e("movieDetailsData", "LOADING")
                }
            }
        }
    }

}