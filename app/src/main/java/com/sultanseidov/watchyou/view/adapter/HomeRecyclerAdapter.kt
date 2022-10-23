package com.sultanseidov.watchyou.view.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sultanseidov.watchyou.data.entities.DiscoverType
import com.sultanseidov.watchyou.data.entities.DiscoverViewsModel
import com.sultanseidov.watchyou.util.Util
import com.sultanseidov.watchyou.R
import es.anthorlop.stories.StoriesManager
import es.anthorlop.stories.datatype.Avatar
import es.anthorlop.stories.datatype.Scene
import es.anthorlop.stories.datatype.Story
import javax.inject.Inject


class HomeRecyclerAdapter @Inject constructor(
    private val glide: RequestManager,
    private val adapterPopularMoviesAdapter: PopularMoviesAdapter,
    private val adapterPopularTvShowsAdapter: PopularTvShowsAdapter,
    private val adapterTopRatedMoviesAdapter: TopRatedMoviesAdapter,
    private val adapterTopRatedTvShowsAdapter: TopRatedTvShowsAdapter,
    private val activity: Activity,
    private val storiesManager: StoriesManager
) : RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder>() {

    var discoverHomeViews: List<DiscoverViewsModel>
        get() = recyclerHomeListDiffer.currentList
        set(value) = recyclerHomeListDiffer.submitList(value)

    private var onItemClickListener: ((Int,DiscoverType) -> Unit)? = null


    override fun getItemViewType(position: Int): Int {
        return when (discoverHomeViews[position].type) {
            DiscoverType.STORY -> TYPE_STORY
            DiscoverType.MOVIES -> TYPE_MOVIES
            DiscoverType.SERIALS -> TYPE_SERIALS
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<DiscoverViewsModel>() {
        override fun areItemsTheSame(
            oldItem: DiscoverViewsModel,
            newItem: DiscoverViewsModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DiscoverViewsModel,
            newItem: DiscoverViewsModel
        ): Boolean {
            return oldItem.type == newItem.type
        }

    }
    private val recyclerHomeListDiffer = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {



        val layout = when (viewType) {
            TYPE_STORY -> R.layout.item_home_story
            TYPE_MOVIES -> R.layout.item_home_movie
            TYPE_SERIALS -> R.layout.item_home_serials
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return MyViewHolder(view)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(
            discoverHomeViews[position],
            adapterPopularMoviesAdapter,
            adapterPopularTvShowsAdapter,
            adapterTopRatedMoviesAdapter,
            adapterTopRatedTvShowsAdapter,
            activity,
            storiesManager,
            onItemClickListener
        )
        /*
            val imvPreMovie = holder.itemView.findViewById<ImageView>(R.id.imvPreMovie)

            val url = moviesList[position].poster_path

            holder.itemView.apply {
                glide.load(IMAGE_URL + url).into(imvPreMovie)
                setOnClickListener {

                    /*
                    onItemClickListener?.let {


                        it(position,holder.itemView)
                    }

                     */
                }
            }

             */


    }

    fun setOnItemClickListener(listener: (Int, DiscoverType) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return discoverHomeViews.size
    }

    class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            dataModel: DiscoverViewsModel,
            adapterPopularMoviesAdapter: PopularMoviesAdapter,
            adapterPopularTvShowsAdapter: PopularTvShowsAdapter,
            adapterTopRatedMoviesAdapter: TopRatedMoviesAdapter,
            adapterTopRatedTvShowsAdapter: TopRatedTvShowsAdapter,
            activity: Activity,
            storiesManager: StoriesManager,
            onItemClickListener: ((Int, DiscoverType) -> Unit)?
        ) {
            when (dataModel.type) {
                DiscoverType.STORY -> bindStory(dataModel,activity,storiesManager,onItemClickListener)
                DiscoverType.MOVIES -> bindMovies(
                    dataModel,
                    adapterPopularMoviesAdapter,
                    adapterTopRatedMoviesAdapter,
                    onItemClickListener
                )
                DiscoverType.SERIALS -> bindSerials(
                    dataModel,
                    adapterPopularTvShowsAdapter,
                    adapterTopRatedTvShowsAdapter,
                    onItemClickListener
                )

            }
        }

        private fun bindMovies(
            dataModel: DiscoverViewsModel,
            adapterMoviesAdapter: PopularMoviesAdapter,
            adapterTopRatedMoviesAdapter: TopRatedMoviesAdapter,
            onItemClickListener: ((Int, DiscoverType) -> Unit)?
        ) {

            itemView.findViewById<RecyclerView>(R.id.recyclerMovie)?.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            if (!dataModel.popularMoviesList.isNullOrEmpty()) {
                itemView.findViewById<TextView>(R.id.txvHomeTitle)?.text = "Popular Movies"
                itemView.findViewById<RecyclerView>(R.id.recyclerMovie)?.adapter = adapterMoviesAdapter

                adapterMoviesAdapter.moviesList = dataModel.popularMoviesList!!
                adapterMoviesAdapter.setOnItemClickListener { i, discoverType ->
                    onItemClickListener?.let {
                        it(i,discoverType)
                    }
                }

            }
            if (!dataModel.topRatedMoviesList.isNullOrEmpty()) {
                itemView.findViewById<AppCompatTextView>(R.id.txvHomeTitle)?.text = "Top Rated Movies"
                itemView.findViewById<RecyclerView>(R.id.recyclerMovie)?.adapter = adapterTopRatedMoviesAdapter

                adapterTopRatedMoviesAdapter.moviesList = dataModel.topRatedMoviesList!!
                adapterTopRatedMoviesAdapter.setOnItemClickListener { i, discoverType ->
                    onItemClickListener?.let {
                        it(i,discoverType)
                    }
                }

            }
        }

        private fun bindStory(
            dataModel: DiscoverViewsModel,
            activity: Activity,
            storiesManager: StoriesManager,
            onItemClickListener: ((Int, DiscoverType) -> Unit)?
        ) {
            dataModel.upcomingMoviesList.let { list ->
                Log.e("viewModel.upcomingMoviesList", "SUCCESS")
                //viewModel.insertMovie(it[0])
                itemView.findViewById<TextView>(R.id.txvHomeTitle)?.text = "Upcoming Movies"

                itemView.setOnClickListener {
                    onItemClickListener?.let {

                        it(position,DiscoverType.STORY)
                    }
                }

                val storyList: ArrayList<Story> = arrayListOf()
                val avatarList: ArrayList<Avatar> = arrayListOf()

                list?.forEach { itemMovie ->
                    val scenes: ArrayList<Scene> = arrayListOf(
                        Scene(
                            itemMovie.id,
                            itemMovie.id,
                            Util.IMAGE_URL + itemMovie.backdrop_path,
                            "http://lombrinus.com",
                            true
                        ), Scene(
                            itemMovie.id + 1,
                            itemMovie.id + 1,
                            Util.IMAGE_URL + itemMovie.poster_path,
                            "http://lombrinus.com",
                            true
                        )

                    )

                    var avatar = Avatar(
                        itemMovie.id,
                        itemMovie.original_title,
                        false,
                        "Test",
                        Util.IMAGE_URL + itemMovie.poster_path,
                        Util.IMAGE_URL + itemMovie.poster_path
                    )

                    var story =
                        Story(
                            0,
                            itemMovie.id,
                            itemMovie.original_title,
                            "Test",
                            Util.IMAGE_URL + itemMovie.poster_path,
                            false, scenes
                        )

                    storyList.add(story)
                    avatarList.add(avatar)
                }

                try {
                    itemView.findViewById<FrameLayout>(R.id.frameLayout)?.addView(
                    //storiesManager.getBarView(activity, avatarList, storyList)
                            storiesManager.getBarViewModePreview(activity, avatarList, storyList)
                    )
                }catch (e:Exception){
                    Log.e("StoriesManager",e.message.toString())
                }


            }

        }


        private fun bindSerials(
            dataModel: DiscoverViewsModel,
            adapterTvShowsAdapter: PopularTvShowsAdapter,
            adapterTopRatedTvShowsAdapter: TopRatedTvShowsAdapter,
            onItemClickListener: ((Int, DiscoverType) -> Unit)?
        ) {
            itemView.findViewById<RecyclerView>(R.id.recyclerTvShows)?.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            if (!dataModel.popularTvShowsList.isNullOrEmpty()) {
                itemView.findViewById<TextView>(R.id.txvHomeTitle)?.text = "Popular Serials"
                itemView.findViewById<RecyclerView>(R.id.recyclerTvShows)?.adapter = adapterTvShowsAdapter

                adapterTvShowsAdapter.tvShowList = dataModel.popularTvShowsList!!
                adapterTvShowsAdapter.setOnItemClickListener { i, view ->
                    onItemClickListener?.let {
                        it(i,view)
                    }
                }



            }

            if (!dataModel.topRatedTvShowsList.isNullOrEmpty()) {
                itemView.findViewById<AppCompatTextView>(R.id.txvHomeTitle)?.text = "Top Rated Serials"
                itemView.findViewById<RecyclerView>(R.id.recyclerTvShows)?.adapter = adapterTopRatedTvShowsAdapter

                adapterTopRatedTvShowsAdapter.tvShowList = dataModel.topRatedTvShowsList!!
                adapterTopRatedTvShowsAdapter.setOnItemClickListener{ i, view ->
                    onItemClickListener?.let {
                        it(i,view)
                    }
                }

            }
        }
    }

    companion object {
        private const val TYPE_STORY = 0
        private const val TYPE_MOVIES = 1
        private const val TYPE_SERIALS = 2
    }

}
