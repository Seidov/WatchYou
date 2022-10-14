package com.sultanseidov.watchyou.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.data.local.AppDatabase
import com.sultanseidov.watchyou.data.local.MovieDao
import com.sultanseidov.watchyou.data.local.TvShowDao
import com.sultanseidov.watchyou.data.repository.IMovieRepository
import com.sultanseidov.watchyou.data.repository.MovieRepository
import com.sultanseidov.watchyou.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.sultanseidov.watchyou.data.remote.ITMDBApi as ITMDBApi1


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "WatchYouApp"
    ).build()


    @Singleton
    @Provides
    fun injectMovieDao(database: AppDatabase) = database.movieDao()


    @Singleton
    @Provides
    fun injectTvShowDao(database: AppDatabase) = database.tvShowDao()


    @Singleton
    @Provides
    fun injectRetrofitApi(): ITMDBApi1 {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(ITMDBApi1::class.java)
    }


    @Singleton
    @Provides
    fun injectNormalRepo(movieDao: MovieDao, tvShowDao: TvShowDao, api: ITMDBApi1) =
        MovieRepository(movieDao, tvShowDao, api) as IMovieRepository


    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
}