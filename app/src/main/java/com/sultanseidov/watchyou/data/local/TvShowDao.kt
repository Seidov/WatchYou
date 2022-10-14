package com.sultanseidov.watchyou.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sultanseidov.watchyou.data.entities.tvshow.TvShowModel

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShowModel: TvShowModel)

    @Delete
    suspend fun deleteTvShow(tvShowModel: TvShowModel)

    @Query("SELECT * FROM shows")
    fun observeTvShows(): LiveData<List<TvShowModel>>
}