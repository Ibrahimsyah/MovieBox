package com.zairussalamdev.moviebox.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zairussalamdev.moviebox.core.domain.model.Movie
import com.zairussalamdev.moviebox.core.utils.ImageNetwork
import com.zairussalamdev.moviebox.databinding.ItemMovieBinding

class MovieAdapter(
    private val listener: (movie: Movie) -> Unit
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movies = listOf<Movie>()

    fun setMovies(movies: List<Movie>?) {
        movies?.let {
            this.movies = it
            notifyDataSetChanged()
        }
    }

    inner class MovieViewHolder(private val view: ItemMovieBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(movie: Movie) {
            with(view) {
                movieTitle.text = movie.title
                movieOverview.text = movie.overview
                movieRating.text = movie.voteAverage.toString()
                moviePoster.load(ImageNetwork.getThumbnailUrl(movie.posterPath)) {
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

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}