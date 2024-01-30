package com.iebayirli.recyclerviewwithdatabinding.api

import com.iebayirli.recyclerviewwithdatabinding.data.article.EmployeeResponse
import com.iebayirli.recyclerviewwithdatabinding.data.currency.RatesResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getEmployees(): Response<EmployeeResponse>
    suspend fun getLatestRates(): Response<RatesResponse>

}