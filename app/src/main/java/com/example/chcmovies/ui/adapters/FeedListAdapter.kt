package com.example.chcmovies.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chcmovies.R
import com.example.chcmovies.data.FeedItem
import com.example.chcmovies.data.MovieItem
import com.example.chcmovies.databinding.FeedItemBinding
import com.example.chcmovies.repo.remote.MovieInfo

class FeedListAdapter(private val callback: (name: MovieItem, view: View) -> Unit) :
    ListAdapter<FeedItem, FeedListAdapter.ViewHolder>(FeedDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FeedItemBinding>(
            layoutInflater, R.layout.feed_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).genre.hashCode().toLong()
    }

    inner class ViewHolder(private val binding: FeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val movieAdapter: MovieListAdapter by lazy {
            val adapter = MovieListAdapter(callback)
            binding.movieList.adapter = adapter
            adapter
        }

        fun bind(feed: FeedItem) {
            binding.genreName.text = feed.genre
            var movieList = mutableListOf<MovieItem>()
            for (movie in feed.movies) {
                movieList.add(MovieItem.getMovieItem(movie))
            }
            movieAdapter.submitList(movieList)
        }
    }
}

object FeedDiffCallBack : DiffUtil.ItemCallback<FeedItem>() {

    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem.genre.hashCode().toLong() == newItem.genre.hashCode().toLong()
    }

    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem == newItem
    }

}