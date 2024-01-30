package com.iebayirli.recyclerviewwithdatabinding.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Currency::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

}
