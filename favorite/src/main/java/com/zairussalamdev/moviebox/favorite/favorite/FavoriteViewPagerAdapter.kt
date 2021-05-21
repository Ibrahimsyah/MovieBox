package com.zairussalamdev.moviebox.favorite.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zairussalamdev.moviebox.core.configs.Constants

class FavoriteViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = com.zairussalamdev.moviebox.favorite.favoritemovies.FavoriteMoviesFragment()
        val type = when (position) {
            0 -> Constants.TYPE_MOVIE
            1 -> Constants.TYPE_TV_SHOW
            else -> Constants.TYPE_MOVIE
        }
        fragment.arguments = Bundle().apply {
            putInt(
                com.zairussalamdev.moviebox.favorite.favoritemovies.FavoriteMoviesFragment.MOVIE_TYPE,
                type
            )
        }
        return fragment
    }
}