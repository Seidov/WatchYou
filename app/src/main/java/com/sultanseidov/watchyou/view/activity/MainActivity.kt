package com.sultanseidov.watchyou.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import es.anthorlop.stories.StoriesManager
import es.anthorlop.stories.interfaces.ImageLoaderInterface
import es.anthorlop.stories.interfaces.StoriesInterface
import kotlin.math.abs

private lateinit var binding: ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(binding.root)

        navController = findNavController(R.id.fragment)

        setupSmoothBottomMenu()
    }


    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu_bottom)
        val menu = popupMenu.menu
        binding.bottomNav.setupWithNavController(menu, navController)
    }





}