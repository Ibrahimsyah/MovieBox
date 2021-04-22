package com.zairussalamdev.moviebox.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import com.zairussalamdev.moviebox.databinding.ItemMovieBinding
import com.zairussalamdev.moviebox.utils.ImageNetwork

class PagedMovieAdapter(
        private val listener: (movie: MovieEntity) -> Unit
) :
        PagedListAdapter<MovieEntity, PagedMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieEntity> =
                object : DiffUtil.ItemCallback<MovieEntity>() {
                    override fun areItemsTheSame(old: MovieEntity, new: MovieEntity): Boolean {
                        return old.title == new.title && old.overview == new.overview
                    }

                    @SuppressLint("DiffUtilEquals")
                    override fun areContentsTheSame(old: MovieEntity, new: MovieEntity): Boolean {
                        return old == new
                    }
                }
    }

    inner class MovieViewHolder(private val view: ItemMovieBinding) :
            RecyclerView.ViewHolder(view.root) {
        fun bind(movie: MovieEntity) {
            with(view) {
                movieTitle.text = movie.title
                movieOverview.text = movie.overview
                movieRating.text = movie.voteAverage.toString()
                moviePoster.load(ImageNetwork.getThumbnailUrl(movie.posterPath as String)) {
                    crossfade(true)
                }
            }
            itemView.setOnClickListener { listener(movie) }
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): PagedMovieAdapter.MovieViewHolder {
        val view = ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position) as MovieEntity)
    }
}