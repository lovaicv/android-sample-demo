package com.my.recyclerviewwithdatabinding.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Currency(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var currencySymbol: String,
    var exchangeRate: Double,
    var exchangeAmount: Double
)
