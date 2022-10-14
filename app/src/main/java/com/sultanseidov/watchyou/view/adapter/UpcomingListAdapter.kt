package com.sultanseidov.watchyou.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sultanseidov.watchyou.R
import com.sultanseidov.watchyou.data.entities.movie.MovieModel
import com.sultanseidov.watchyou.util.Util.IMAGE_URL
import javax.inject.Inject

class UpcomingListAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<UpcomingListAdapter.MyViewHolder>() {

    //private var onItemClickListener: ((Int,View) -> Unit)? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var upcomingMovie: List<MovieModel>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.upc_item_model, parent, false)
        return MyViewHolder(view)
    }

    fun setOnItemClickListener(listener: (Int,View) -> Unit) {
        //onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imvPreUpcomingMovie = holder.itemView.findViewById<ImageView>(R.id.imvPreUpcomingMovie)
        val txvPreUpcomingMovie = holder.itemView.findViewById<TextView>(R.id.txvPreUpcomingMovie)

        txvPreUpcomingMovie.text=upcomingMovie[position].title
        val url = upcomingMovie[position].poster_path
        holder.itemView.apply {
            glide.load(IMAGE_URL+url).into(imvPreUpcomingMovie)
            setOnClickListener {



                /*
                onItemClickListener?.let {


                    it(position,holder.itemView)
                }

                 */
            }
        }


    }

    override fun getItemCount(): Int {
        return upcomingMovie.size
    }
}
