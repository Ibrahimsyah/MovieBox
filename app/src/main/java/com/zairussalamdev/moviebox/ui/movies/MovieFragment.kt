package com.zairussalamdev.moviebox.ui.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zairussalamdev.moviebox.App
import com.zairussalamdev.moviebox.configs.MovieType
import com.zairussalamdev.moviebox.databinding.FragmentMoviesBinding
import com.zairussalamdev.moviebox.ui.adapter.MovieAdapter
import com.zairussalamdev.moviebox.ui.detail.DetailActivity
import javax.inject.Inject

class MovieFragment : Fragment() {
    companion object {
        const val MOVIE_TYPE = "MOVIE_TYPE"
    }

    @Inject
    lateinit var movieViewModel: MovieViewModel

    private lateinit var binding: FragmentMoviesBinding

    override fun onAttach(context: Context) {
        (context.applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieType = arguments?.getInt(MOVIE_TYPE)

        val adapter = MovieAdapter {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.MOVIE_ID, it.id)
            intent.putExtra(DetailActivity.MOVIE_TYPE, movieType)
            startActivity(intent)
        }

        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = adapter
        }

        val data = movieViewModel.let {
            if (movieType == MovieType.TYPE_MOVIE) it.getMovieList()
            else it.getTvShowsList()
        }

        data.observe(viewLifecycleOwner, {
            adapter.setMovies(it)
        })

        movieViewModel.getLoading().observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        movieViewModel.getErrorMessage().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                binding.errorMessage.apply {
                    visibility = View.VISIBLE
                    text = it
                }
            } else {
                binding.errorMessage.visibility = View.GONE
            }
        })
    }
}