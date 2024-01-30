package com.my.recyclerviewwithdatabinding.scene.main

import com.my.recyclerviewwithdatabinding.R
import com.my.recyclerviewwithdatabinding.base.BaseAdapter
import com.my.recyclerviewwithdatabinding.data.movie.Movie
import com.my.recyclerviewwithdatabinding.databinding.ItemMovieBinding

class MovieAdapter(
    private val list: List<Movie>,
    private var filteredMovieList: List<Movie>,
    private val movieListener: MovieListener
) : BaseAdapter<ItemMovieBinding, Movie>(list, filteredMovieList) {

    override val layoutId: Int = R.layout.item_movie

    override fun bind(binding: ItemMovieBinding, item: Movie) {
        binding.apply {
            movie = item
            listener = movieListener
            executePendingBindings()
        }
    }
}

interface MovieListener {
    fun onMovieClicked(movie: Movie)
    fun onFavouriteClicked(movie: Movie)
}