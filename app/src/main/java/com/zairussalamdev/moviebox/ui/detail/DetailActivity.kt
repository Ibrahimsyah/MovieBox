package com.zairussalamdev.moviebox.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.zairussalamdev.moviebox.R
import com.zairussalamdev.moviebox.core.configs.Constants
import com.zairussalamdev.moviebox.core.data.Resource
import com.zairussalamdev.moviebox.core.domain.model.Detail
import com.zairussalamdev.moviebox.core.domain.model.Movie
import com.zairussalamdev.moviebox.core.utils.ImageNetwork
import com.zairussalamdev.moviebox.databinding.ActivityDetailBinding
import com.zairussalamdev.moviebox.ui.adapter.MovieGenreAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        const val MOVIE_TYPE = "MOVIE_TYPE"
    }

    private val detailViewModel: DetailViewModel by viewModel()

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailEntity: Detail
    private lateinit var genreAdapter: MovieGenreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        genreAdapter = MovieGenreAdapter()
        val movieId = intent.getIntExtra(MOVIE_ID, 0)
        val movieType = intent.getIntExtra(MOVIE_TYPE, Constants.TYPE_MOVIE)
        var isMovieFavorite = false

        with(binding.rvMovieGenre) {
            layoutManager = LinearLayoutManager(
                    this@DetailActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
            )
            setHasFixedSize(true)
            adapter = genreAdapter
        }

        val data = detailViewModel.let {
            if (movieType == Constants.TYPE_MOVIE) it.getMovieDetail(movieId)
            else it.getTvShowDetail(movieId)
        }

        data.observe(this, {
            when (it) {
                is Resource.Success -> {
                    hideErrorMessage()
                    showLoading(false)
                    showData(it.data)
                    detailEntity = it.data as Detail
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Error -> {
                    showLoading(false)
                    showErrorMessage(it.message as String)
                }
            }
        })

        detailViewModel.checkIsMovieFavorite(movieId).observe(this, {
            isMovieFavorite = it
            val icon = if (it) R.drawable.ic_favorite_active else R.drawable.ic_favorite
            binding.fab.setImageResource(icon)
        })

        binding.fab.setOnClickListener {
            val movie = mapDetailToMovie(detailEntity, movieType)
            if (isMovieFavorite) {
                detailViewModel.deleteMovieFromFavorite(movie)
                Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show()
            } else {
                detailViewModel.addMovieToFavorite(movie)
                Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showData(detail: Detail?) {
        detail?.let {
            val rating = resources.getString(R.string.movie_rating)
            with(binding) {
                moviePoster.load(ImageNetwork.getFullSizeUrl(it.posterPath)) {
                    crossfade(true)
                }
                movieTitle.text = it.title
                moviePopularity.text = it.popularity.toString()
                movieRating.text = String.format(rating, it.voteAverage)
                movieTagline.text = it.tagLine
                movieOverview.text = it.overview
                movieStatus.text = it.status
                movieHomepage.text = it.homepage
                genreAdapter.setGenres(it.genres)
                overviewLabel.visibility = View.VISIBLE
                statusLabel.visibility = View.VISIBLE
                homepageLabel.visibility = View.VISIBLE
                popularityLabel.visibility = View.VISIBLE
                ratingLabel.visibility = View.VISIBLE
            }
        }
    }

    private fun showErrorMessage(message: String) {
        binding.fab.visibility = View.GONE
        binding.errorMessage.apply {
            visibility = View.VISIBLE
            text = message
        }
    }

    private fun hideErrorMessage() {
        binding.errorMessage.visibility = View.GONE
    }

    private fun showLoading(state: Boolean) {
        binding.fab.visibility = if (state) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun mapDetailToMovie(detail: Detail, movieType: Int): Movie {
        return Movie(
            detail.id,
            detail.overview,
            detail.title,
            detail.posterPath,
            detail.voteAverage,
            movieType
        )
    }
}