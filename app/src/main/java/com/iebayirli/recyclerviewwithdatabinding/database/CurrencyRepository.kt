package com.iebayirli.recyclerviewwithdatabinding.database

import android.util.Log
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyDao: CurrencyDao
) {

    suspend fun getAllCurrencies(): List<Currency> {
        return currencyDao.getAllCurrencies()
    }

    suspend fun insertCurrency(currency: Currency) {
        val existingUser = currencyDao.getCurrency(currency.currencySymbol)
        if (existingUser == null) {
            Log.d(
                "check",
                "currency not found insert ${currency.currencySymbol} ${currency.exchangeRate}"
            )
            currencyDao.insertCurrency(currency)
        } else {
            Log.d(
                "check",
                "currency found update ${currency.currencySymbol} from ${existingUser.exchangeRate} to ${currency.exchangeRate} "
            )
            currencyDao.updateCurrency(currency)
        }
    }

    suspend fun deleteCurrency(user: Currency) {
        currencyDao.deleteCurrency(user)
    }

    suspend fun deleteAll() {
        currencyDao.deleteAll()
    }
}