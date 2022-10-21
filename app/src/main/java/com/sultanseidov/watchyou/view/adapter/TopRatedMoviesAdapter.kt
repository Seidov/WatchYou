package com.sultanseidov.watchyou.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.data.entities.DiscoverType
import com.sultanseidov.watchyou.data.entities.movie.MovieModel
import com.sultanseidov.watchyou.util.Util
import javax.inject.Inject

class TopRatedMoviesAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<TopRatedMoviesAdapter.MyViewHolder>() {

    private var onItemClickListener: ((Int,DiscoverType) -> Unit)? = null


    var moviesList: List<MovieModel>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)
    private val diffUtil = object : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

    }
    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item_model, parent, false)
        return MyViewHolder(view)
    }

    fun setOnItemClickListener(listener: (Int, DiscoverType) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imvPreUpcomingMovie = holder.itemView.findViewById<ImageView>(R.id.imvPreMovie)
        val url = moviesList[position].poster_path
        holder.itemView.apply {
            glide.load(Util.IMAGE_URL + url).into(imvPreUpcomingMovie)
            setOnClickListener {
                onItemClickListener?.let {
                    it(moviesList[position].id,DiscoverType.MOVIES)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}