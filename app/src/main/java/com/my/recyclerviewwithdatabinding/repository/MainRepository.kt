package com.my.recyclerviewwithdatabinding.repository

import com.my.recyclerviewwithdatabinding.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun getEmployee() = apiHelper.getEmployees()
    suspend fun getLatestRates() = apiHelper.getLatestRates()

}