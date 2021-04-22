package com.zairussalamdev.moviebox.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zairussalamdev.moviebox.configs.Constants
import com.zairussalamdev.moviebox.ui.favoritemovies.FavoriteMoviesFragment

class FavoriteViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FavoriteMoviesFragment()
        val type = when (position) {
            0 -> Constants.TYPE_MOVIE
            1 -> Constants.TYPE_TV_SHOW
            else -> Constants.TYPE_MOVIE
        }
        fragment.arguments = Bundle().apply {
            putInt(FavoriteMoviesFragment.MOVIE_TYPE, type)
        }
        return fragment
    }
}