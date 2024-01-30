package com.my.recyclerviewwithdatabinding.data.movie

import androidx.annotation.DrawableRes
import com.my.recyclerviewwithdatabinding.base.ListAdapterItem
import java.io.Serializable

data class Movie(
    val name: String,
    val date: String,
    val rating: String,
    @DrawableRes val image: Int,
    var favourite: Boolean = false,
    override var id: Long = 0
) : ListAdapterItem, Serializable