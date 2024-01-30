package com.my.recyclerviewwithdatabinding.api

import com.my.recyclerviewwithdatabinding.data.article.EmployeeResponse
import com.my.recyclerviewwithdatabinding.data.currency.RatesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getEmployees(): Response<EmployeeResponse>

    @GET("latest")
    suspend fun getLatestRates(): Response<RatesResponse>
}