package com.zairussalamdev.moviebox.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zairussalamdev.moviebox.databinding.FragmentTvShowsBinding

class TvShowsFragment : Fragment() {
    private lateinit var binding: FragmentTvShowsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowsBinding.inflate(layoutInflater)
        return binding.root
    }
}