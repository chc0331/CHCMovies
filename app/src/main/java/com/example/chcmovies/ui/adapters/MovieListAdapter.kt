package com.example.chcmovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chcmovies.R
import com.example.chcmovies.data.MovieItem
import com.example.chcmovies.databinding.MovieItemBinding

class MovieListAdapter(private val callback: (movie: MovieItem, view: View) -> Unit) :
    ListAdapter<MovieItem, MovieListAdapter.ViewHolder>(MovieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<MovieItemBinding>(
            layoutInflater, R.layout.movie_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), callback)
    }

    inner class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieItem, callback: (movie: MovieItem, view: View) -> Unit) {
            binding.movie = item
            binding.root.setOnClickListener {
                callback(item, binding.movieImage)
            }
        }
    }
}

object MovieDiffCallback : DiffUtil.ItemCallback<MovieItem>() {

    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.movieName == newItem.movieName
    }

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem == newItem
    }

}