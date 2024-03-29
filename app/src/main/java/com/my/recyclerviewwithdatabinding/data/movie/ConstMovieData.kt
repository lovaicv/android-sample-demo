package com.my.recyclerviewwithdatabinding.data.movie

import com.my.recyclerviewwithdatabinding.R

object ConstMovieData {

    var movieList = mutableListOf(
        Movie(
            name = "The Shawshank Redemption",
            date = "1994",
            rating = "9.2",
            image = R.drawable.image_1,
            id = 0,
        ),
        Movie(
            name = "The Godfather",
            date = "1972",
            rating = "9.1",
            image = R.drawable.image_2,
            id = 1,
        ),
        Movie(
            name = "The Godfather: Part II",
            date = "1974",
            rating = "9.0",
            image = R.drawable.image_3,
            id = 2,
        ),
        Movie(
            name = "The Dark Knight",
            date = "2008",
            rating = "9.0",
            image = R.drawable.image_4,
            id = 3,
        ),
        Movie(
            name = "12 Angry Men",
            date = "1957",
            rating = "8.9",
            image = R.drawable.image_5,
            id = 4,
        )
    )
}