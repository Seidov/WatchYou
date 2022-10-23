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
import com.sultanseidov.watchyou.data.entities.tvshow.TvShowModel
import com.sultanseidov.watchyou.util.Util
import javax.inject.Inject

class TopRatedTvShowsAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<TopRatedTvShowsAdapter.MyViewHolder>() {


    private var onItemClickListener: ((Int, DiscoverType) -> Unit)? = null


    var tvShowList: List<TvShowModel>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)
    private val diffUtil = object : DiffUtil.ItemCallback<TvShowModel>() {
        override fun areItemsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
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
        val url = tvShowList[position].poster_path
        holder.itemView.apply {
            glide.load(Util.IMAGE_URL + url).into(imvPreUpcomingMovie)
            setOnClickListener {
                onItemClickListener?.let {
                    it(tvShowList[position].id, DiscoverType.SERIALS)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}