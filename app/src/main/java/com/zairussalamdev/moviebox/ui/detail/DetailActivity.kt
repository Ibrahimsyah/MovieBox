package com.zairussalamdev.moviebox.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.zairussalamdev.moviebox.App
import com.zairussalamdev.moviebox.R
import com.zairussalamdev.moviebox.configs.Constants
import com.zairussalamdev.moviebox.data.local.entities.DetailEntity
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import com.zairussalamdev.moviebox.databinding.ActivityDetailBinding
import com.zairussalamdev.moviebox.ui.adapter.MovieGenreAdapter
import com.zairussalamdev.moviebox.utils.ImageNetwork
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        const val MOVIE_TYPE = "MOVIE_TYPE"
    }

    @Inject
    lateinit var detailViewModel: DetailViewModel

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailEntity: DetailEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra(MOVIE_ID, 0)
        val movieType = intent.getIntExtra(MOVIE_TYPE, Constants.TYPE_MOVIE)
        val genreAdapter = MovieGenreAdapter()
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

        detailViewModel.getLoading().observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        detailViewModel.getErrorMessage().observe(this, {
            if (it.isNotEmpty()) {
                binding.errorMessage.apply {
                    visibility = View.VISIBLE
                    text = it
                }
            } else {
                binding.errorMessage.visibility = View.GONE
            }
        })

        val data = detailViewModel.let {
            if (movieType == Constants.TYPE_MOVIE) it.getMovieDetail(movieId)
            else it.getTvShowDetail(movieId)
        }

        data.observe(this, {
            detailEntity = it
            val rating = resources.getString(R.string.movie_rating)
            with(binding) {
                moviePoster.load(ImageNetwork.getFullSizeUrl(it.posterPath as String))
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

    private fun mapDetailToMovie(detailEntity: DetailEntity, movieType: Int): MovieEntity {
        return MovieEntity(
                detailEntity.id,
                detailEntity.overview,
                detailEntity.title,
                detailEntity.posterPath,
                detailEntity.voteAverage,
                movieType
        )
    }
}