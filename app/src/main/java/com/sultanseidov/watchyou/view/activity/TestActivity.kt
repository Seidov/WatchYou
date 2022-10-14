package com.sultanseidov.watchyou.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.data.entities.base.Status
import com.sultanseidov.watchyou.util.Util
import com.sultanseidov.watchyou.view.viewmodel.MovieViewModel
import es.anthorlop.stories.StoriesManager
import es.anthorlop.stories.datatype.Avatar
import es.anthorlop.stories.datatype.Scene
import es.anthorlop.stories.datatype.Story

class TestActivity : AppCompatActivity() {
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var bundle :Bundle ?=intent.extras
        var movieId = bundle!!.getString("movieId")

        Toast.makeText(this, ""+movieId, Toast.LENGTH_SHORT).show()

        //subscribeToObservers()
    }

}