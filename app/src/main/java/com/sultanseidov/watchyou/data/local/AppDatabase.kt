package com.sultanseidov.watchyou.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sultanseidov.watchyou.data.entities.movie.MovieModel
import com.sultanseidov.watchyou.data.entities.tvshow.TvShowModel

@Database(entities = [MovieModel::class, TvShowModel::class], exportSchema = false, version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
}