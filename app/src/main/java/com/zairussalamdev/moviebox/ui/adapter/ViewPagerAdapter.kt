package com.zairussalamdev.moviebox.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zairussalamdev.moviebox.ui.movies.MoviesFragment
import com.zairussalamdev.moviebox.utils.MovieType

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = MoviesFragment()
        val type = when (position) {
            0 -> MovieType.TYPE_MOVIE
            1 -> MovieType.TYPE_TVSHOWS
            else -> MovieType.TYPE_MOVIE
        }
        fragment.arguments = Bundle().apply {
            putInt(MoviesFragment.MOVIE_TYPE, type)
        }
        return fragment
    }
}