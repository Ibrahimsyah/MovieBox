package com.zairussalamdev.moviebox.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zairussalamdev.moviebox.databinding.FragmentMoviesBinding
import com.zairussalamdev.moviebox.ui.adapter.MovieAdapter
import com.zairussalamdev.moviebox.ui.detail.DetailActivity
import com.zairussalamdev.moviebox.utils.ViewModelFactory

class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance()
        val movieViewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)
        val adapter = MovieAdapter {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.MOVIE_ID, it.id)
            startActivity(intent)
        }

        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = adapter
        }

        movieViewModel.getMovieList().observe(viewLifecycleOwner, {
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