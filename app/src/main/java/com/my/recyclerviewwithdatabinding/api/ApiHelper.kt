package com.my.recyclerviewwithdatabinding.api

import com.my.recyclerviewwithdatabinding.data.article.EmployeeResponse
import com.my.recyclerviewwithdatabinding.data.currency.RatesResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getEmployees(): Response<EmployeeResponse>
    suspend fun getLatestRates(): Response<RatesResponse>

}