package com.sultanseidov.watchyou.di

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.data.local.AppDatabase
import com.sultanseidov.watchyou.data.local.MovieDao
import com.sultanseidov.watchyou.data.local.TvShowDao
import com.sultanseidov.watchyou.data.remote.ITMDBApi
import com.sultanseidov.watchyou.data.repository.IMovieRepository
import com.sultanseidov.watchyou.data.repository.MovieRepository
import com.sultanseidov.watchyou.util.Util.BASE_URL
import com.sultanseidov.watchyou.view.customview.ZoomOutViewPage.ZoomOutPageTransformer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.anthorlop.stories.StoriesManager
import es.anthorlop.stories.interfaces.ImageLoaderInterface
import es.anthorlop.stories.interfaces.StoriesInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


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
    fun injectRetrofitApi(): ITMDBApi {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(ITMDBApi::class.java)
    }


    @Singleton
    @Provides
    fun injectNormalRepo(movieDao: MovieDao, tvShowDao: TvShowDao, api: ITMDBApi) =
        MovieRepository(movieDao, tvShowDao, api) as IMovieRepository


    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )


    @Singleton
    @Provides
    fun injectStoryView()=StoriesManager.getInstance().apply {
        this.setImageInterface(object : ImageLoaderInterface {
            override fun loadImage(context: Context, url: String, imageView: ImageView) {

                if (url.isEmpty()) return

                Glide.with(context).load(url).into(imageView)

            }
        })
        this.setInterface(object : StoriesInterface {
            override fun onShowMoreClicked(
                activity: Activity, idStory: Int, nameStory: String, storyType: String,
                idScene: Int, link: String
            ) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(link)
                activity.startActivity(intent)

                Log.d(toString(), " > > > onShowMoreClicked: story = $idStory, scene = $idScene")
            }

            override fun onAvatarClicked(position: Int, id: Int, name: String, storyType: String) {
                // enviar analitica
                Log.d(toString(), " > onAvatarClicked: position = $position, story = $id")
            }

            override fun onStoryDetailStarted(id: Int, name: String, type: String) {
                // enviar analitica
                Log.d(toString(), " > > onStoryDetailStarted:  story = $id")
            }

            override fun onSceneDetailStarted(
                id: Int,
                idStory: Int,
                nameStory: String,
                storyType: String
            ) {
                // enviar analitica
                Log.d(toString(), " > > > onSceneDetailStarted:  scene = $id")
            }

            override fun onStoriesDetailClosed(fromUser: Boolean) {
                // enviar analitica
                Log.d(toString(), " > onStoriesDetailClosed: fromUser = $fromUser")
            }

            override fun getViewPagerTransformer(): ViewPager2.PageTransformer {
                return ZoomOutPageTransformer()
            }
        })
    }


}