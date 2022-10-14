package com.sultanseidov.watchyou.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.sultanseidov.watchyou.App
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.data.entities.base.Status
import com.sultanseidov.watchyou.databinding.FragmentDiscoverBinding
import com.sultanseidov.watchyou.util.Util.IMAGE_URL
import com.sultanseidov.watchyou.view.activity.TestActivity
import com.sultanseidov.watchyou.view.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import es.anthorlop.stories.StoriesManager
import es.anthorlop.stories.datatype.Avatar
import es.anthorlop.stories.datatype.Scene
import es.anthorlop.stories.datatype.Story
import es.anthorlop.stories.interfaces.StoriesInterface


@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MovieViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //findNavController().navigate(R.id.action_discoverFragment_to_libraryFragment)

        StoriesManager.getInstance().set("http://lombrinus.com", 5, 5)



        viewModel.fetchUpcomingMovies()

        subscribeToObservers()

        StoriesManager.getInstance().setInterface(object : StoriesInterface {
            override fun onShowMoreClicked(
                activity: Activity, idStory: Int, nameStory: String, storyType: String,
                idScene: Int, link: String
            ) {

                /*
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(link)
                activity.startActivity(intent)
                 */


                val intent = Intent(requireActivity(), TestActivity::class.java)
                intent.putExtra("movieId", idStory.toString())
                activity.startActivity(intent)

                //requireActivity().findNavController(activity.taskId).navigate(R.id.action_discoverFragment_to_libraryFragment)
                //findNavController().navigate(R.id.action_discoverFragment_to_libraryFragment)

                Toast.makeText(requireActivity(), "onShowMoreClicked", Toast.LENGTH_SHORT).show()
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
                return App.ZoomOutPageTransformer()
            }
        })


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == StoriesManager.UE_STORIES_ACTIVITY_REQUEST_CODE) {
            val container = binding.storiesBarContainer
            StoriesManager.Companion.getInstance().refreshBarView(container)

        }
    }

    private fun subscribeToObservers() {

        viewModel.upcomingMoviesList.observe(viewLifecycleOwner) { result ->

            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.results?.let { list ->
                        Log.e("viewModel.upcomingMoviesList", "SUCCESS")
                        //viewModel.insertMovie(it[0])
                        val storyList: ArrayList<Story> = arrayListOf()
                        val avatarList: ArrayList<Avatar> = arrayListOf()

                        list.forEach { itemMovie ->
                            val scenes: ArrayList<Scene> = arrayListOf(
                                Scene(
                                    itemMovie.id,
                                    itemMovie.id,
                                    IMAGE_URL + itemMovie.backdrop_path,
                                    "http://lombrinus.com",
                                    true
                                ), Scene(
                                    itemMovie.id + 1,
                                    itemMovie.id + 1,
                                    IMAGE_URL + itemMovie.poster_path,
                                    "http://lombrinus.com",
                                    true
                                )

                            )

                            var avatar = Avatar(
                                itemMovie.id,
                                itemMovie.original_title,
                                false,
                                "Test",
                                IMAGE_URL + itemMovie.poster_path,
                                IMAGE_URL + itemMovie.poster_path
                            )

                            var story =
                                Story(
                                    0,
                                    itemMovie.id,
                                    itemMovie.original_title,
                                    "Test",
                                    IMAGE_URL + itemMovie.poster_path,
                                    false, scenes
                                )

                            storyList.add(story)
                            avatarList.add(avatar)
                        }

                        binding.storiesBarContainer.addView(
                            StoriesManager.getInstance().getBarView(requireActivity(), avatarList, storyList)
                            //StoriesManager.getInstance().getBarViewModePreview(requireActivity(), avatarList, storyList)
                        )


                    }
                }

                Status.ERROR -> {
                    result.message?.let {
                        Log.e("viewModel.upcomingMoviesList", "ERROR")
                    }
                }

                Status.LOADING -> {
                    Log.e("viewModel.upcomingMoviesList", "LOADING")
                }
            }
        }
        viewModel.popularMoviesList.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.results?.let {
                        Log.e("viewModel.popularMoviesList", "SUCCESS")
                    }
                }

                Status.ERROR -> {
                    result.message?.let {
                        Log.e("viewModel.popularMoviesList", "ERROR")
                    }
                }

                Status.LOADING -> {
                    Log.e("viewModel.popularMoviesList", "LOADING")
                }
            }
        }
        viewModel.topRatedMoviesList.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.results?.let {
                        Log.e("viewModel.topRatedMoviesList", "SUCCESS")
                    }
                }

                Status.ERROR -> {
                    result.message?.let {
                        Log.e("viewModel.topRatedMoviesList", "ERROR")

                    }
                }

                Status.LOADING -> {
                    Log.e("viewModel.topRatedMoviesList", "LOADING")

                }
            }
        }

        viewModel.popularTvShowsList.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.results?.let { list ->
                        Log.e("viewModel.popularTvShowsList", "SUCCESS")

                        if (!list.isNullOrEmpty()) {
                            //viewModel.insertTvShow(list[0])

                        }
                    }
                }

                Status.ERROR -> {
                    result.message?.let {
                        Log.e("viewModel.popularTvShowsList", "ERROR")
                    }
                }

                Status.LOADING -> {
                    Log.e("viewModel.popularTvShowsList", "LOADING")
                }
            }
        }
        viewModel.topRatedTvShowsList.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.results?.let {
                        Log.e("viewModel.topRatedTvShowsList", "SUCCESS")
                    }
                }

                Status.ERROR -> {
                    result.message?.let {
                        Log.e("viewModel.topRatedTvShowsList", "ERROR")
                    }
                }

                Status.LOADING -> {
                    Log.e("viewModel.topRatedTvShowsList", "LOADING")
                }
            }
        }

        viewModel.multiSearchResultList.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let {
                        Log.e("viewModel.multiSearchResultList", "SUCCESS")
                    }
                }

                Status.ERROR -> {
                    result.message?.let {
                        Log.e("viewModel.topRatedTvShowsList", "ERROR")
                    }
                }

                Status.LOADING -> {
                    Log.e("viewModel.topRatedTvShowsList", "LOADING")
                }
            }
        }


        viewModel.upcomingStoryList.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let { model ->
                        Log.e("viewModel.upcomingMoviesList", "SUCCESS")
                        //viewModel.insertMovie(it[0])


                    }
                }

                Status.ERROR -> {
                    result.message?.let {
                        Log.e("viewModel.upcomingMoviesList", "ERROR")
                    }
                }

                Status.LOADING -> {
                    Log.e("viewModel.upcomingMoviesList", "LOADING")
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}