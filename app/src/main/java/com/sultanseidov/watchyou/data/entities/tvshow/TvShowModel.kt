package com.sultanseidov.watchyou.data.entities.tvshow

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shows")
data class TvShowModel(
    @NonNull
    @PrimaryKey
    val id: Int,
    val backdrop_path: String?,
    val first_air_date: String,
    val name: String,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val vote_average: Double,
    val vote_count: Int
)