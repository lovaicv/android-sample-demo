package com.my.recyclerviewwithdatabinding.api

import com.my.recyclerviewwithdatabinding.data.article.EmployeeResponse
import com.my.recyclerviewwithdatabinding.data.currency.RatesResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getEmployees(): Response<EmployeeResponse> = apiService.getEmployees()
    override suspend fun getLatestRates(): Response<RatesResponse> = apiService.getLatestRates()
}