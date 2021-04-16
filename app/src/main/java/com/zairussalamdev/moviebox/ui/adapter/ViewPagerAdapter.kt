package com.zairussalamdev.moviebox.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zairussalamdev.moviebox.ui.movies.MoviesFragment
import com.zairussalamdev.moviebox.ui.tvshows.TvShowsFragment

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    private val fragments: List<Fragment> = listOf(MoviesFragment(), TvShowsFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}