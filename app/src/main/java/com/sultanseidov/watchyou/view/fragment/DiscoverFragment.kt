package com.sultanseidov.watchyou.view.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.data.entities.DiscoverType
import com.sultanseidov.watchyou.data.entities.base.Status
import com.sultanseidov.watchyou.databinding.FragmentDiscoverBinding
import com.sultanseidov.watchyou.view.activity.UpcomingDetailsActivity
import com.sultanseidov.watchyou.view.adapter.HomeRecyclerAdapter
import com.sultanseidov.watchyou.view.customview.ZoomOutViewPage.ZoomOutPageTransformer
import com.sultanseidov.watchyou.view.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import es.anthorlop.stories.StoriesManager
import es.anthorlop.stories.interfaces.StoriesInterface
import javax.inject.Inject


@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    @Inject
    lateinit var adapter: HomeRecyclerAdapter
    @Inject
    lateinit var storiesManager: StoriesManager

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

        initStoryView()

        initHomeRecyclerview()

        viewModel.fetchHomeList()

        subscribeToObservers()

    }

    private fun initStoryView() {
        storiesManager.set("", 5, 5)
        storiesManager.setInterface(object : StoriesInterface {
            override fun onShowMoreClicked(
                activity: Activity, idStory: Int, nameStory: String, storyType: String,
                idScene: Int, link: String
            ) {

                /*
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(link)
                activity.startActivity(intent)
                 */


                val intent = Intent(requireActivity(), UpcomingDetailsActivity::class.java)
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
                return ZoomOutPageTransformer()
            }
        })
    }

    private fun initHomeRecyclerview() {
        binding.rcvHomeList.adapter = adapter
        binding.rcvHomeList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.setOnItemClickListener { onClickText, discoverType ->

            when (discoverType) {
                DiscoverType.MOVIES ->{
                    val bundle = Bundle()
                    bundle.putString("movieId", onClickText.toString())
                    Navigation.findNavController(requireView()).navigate(R.id.action_discoverFragment_to_movieDetailsFragment,bundle)
                }

                DiscoverType.SERIALS -> {
                    val bundle = Bundle()
                    bundle.putString("tvId", onClickText.toString())
                    Navigation.findNavController(requireView()).navigate(R.id.action_discoverFragment_to_tvDetailsFragment,bundle)
                }

                else -> {}
            }
        }
    }

    private fun subscribeToObservers() {
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
                    Log.e("viewModel.topRatedTvShowsList", "TVSHOWS")
                }
            }
        }
        viewModel.homeListResultList.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let {
                        Log.e("viewModel.homeListResultList", "SUCCESS")
                        adapter.discoverHomeViews = it
                    }
                }

                Status.ERROR -> {
                    result.message?.let {
                        Log.e("viewModel.homeListResultList", "ERROR")
                    }
                }

                Status.LOADING -> {
                    Log.e("viewModel.homeListResultList", "TVSHOWS")
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}