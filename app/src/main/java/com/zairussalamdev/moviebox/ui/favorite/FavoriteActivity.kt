package com.zairussalamdev.moviebox.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.zairussalamdev.moviebox.databinding.ActivityFavoriteBinding
import com.zairussalamdev.moviebox.ui.adapter.FavoriteViewPagerAdapter
import com.zairussalamdev.moviebox.ui.main.MainActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val adapter = FavoriteViewPagerAdapter(this@FavoriteActivity)
            viewPager.adapter = adapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = MainActivity.TAB_TITLES[position]
            }.attach()
        }
    }
}