package com.zairussalamdev.moviebox.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import com.zairussalamdev.moviebox.databinding.ItemMovieBinding
import com.zairussalamdev.moviebox.utils.ImageNetwork

class MovieAdapter(private val listener: (movie: MovieEntity) -> Unit) :
        RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies = listOf<MovieEntity>()

    fun setMovies(movies: List<MovieEntity>) {
        this.movies = movies
        this.notifyDataSetChanged()
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
    ): MovieAdapter.MovieViewHolder {
        val view = ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}