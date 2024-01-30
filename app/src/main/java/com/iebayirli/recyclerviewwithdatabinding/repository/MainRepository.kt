package com.iebayirli.recyclerviewwithdatabinding.repository

import com.iebayirli.recyclerviewwithdatabinding.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun getEmployee() = apiHelper.getEmployees()
    suspend fun getLatestRates() = apiHelper.getLatestRates()

}