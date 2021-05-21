package com.zairussalamdev.moviebox.favorite.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.zairussalamdev.moviebox.favorite.databinding.ActivityFavoriteBinding
import com.zairussalamdev.moviebox.favorite.di.favoriteModule
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    companion object {
        val TAB_TITLES = arrayOf("Movies", "TV Shows")
    }

    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        with(binding) {
            val adapter = FavoriteViewPagerAdapter(this@FavoriteActivity)
            viewPager.adapter = adapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = TAB_TITLES[position]
            }.attach()
        }
    }
}