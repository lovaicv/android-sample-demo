package com.my.recyclerviewwithdatabinding.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM users")
    suspend fun getAllCurrencies(): List<Currency>

    @Query("SELECT * FROM users WHERE currencySymbol = :symbol")
    suspend fun getCurrency(symbol: String): Currency?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(user: Currency)

    @Update
    suspend fun updateCurrency(user: Currency)

    @Delete
    suspend fun deleteCurrency(user: Currency)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}
