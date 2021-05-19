package com.zairussalamdev.moviebox.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zairussalamdev.moviebox.core.domain.model.Genre
import com.zairussalamdev.moviebox.databinding.ItemGenreBinding

class MovieGenreAdapter : RecyclerView.Adapter<MovieGenreAdapter.GenreViewHolder>() {
    private var genreList: List<Genre> = listOf()

    fun setGenres(genreList: List<Genre>) {
        this.genreList = genreList
        this.notifyDataSetChanged()
    }

    inner class GenreViewHolder(private val view: ItemGenreBinding) :
            RecyclerView.ViewHolder(view.root) {
        fun bind(genre: Genre) {
            view.genre.text = genre.name
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): MovieGenreAdapter.GenreViewHolder {
        val view = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieGenreAdapter.GenreViewHolder, position: Int) {
        holder.bind(genreList[position])
    }

    override fun getItemCount(): Int = genreList.size
}