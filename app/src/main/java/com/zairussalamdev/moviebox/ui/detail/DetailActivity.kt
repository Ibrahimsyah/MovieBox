package com.zairussalamdev.moviebox.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.zairussalamdev.moviebox.R
import com.zairussalamdev.moviebox.databinding.ActivityDetailBinding
import com.zairussalamdev.moviebox.ui.adapter.MovieGenreAdapter
import com.zairussalamdev.moviebox.utils.ImageNetwork
import com.zairussalamdev.moviebox.utils.MovieType
import com.zairussalamdev.moviebox.utils.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        const val MOVIE_TYPE = "MOVIE_TYPE"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra(MOVIE_ID, 0)
        val movieType = intent.getIntExtra(MOVIE_TYPE, MovieType.TYPE_MOVIE)
        val factory = ViewModelFactory.getInstance()
        val detailViewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)
        val genreAdapter = MovieGenreAdapter()

        with(binding.rvMovieGenre) {
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
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
            if (movieType == MovieType.TYPE_MOVIE) it.getMovieDetail(movieId)
            else it.getTvShowDetail(movieId)
        }

        data.observe(this, {
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
    }
}