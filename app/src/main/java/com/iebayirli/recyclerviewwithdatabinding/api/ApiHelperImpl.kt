package com.iebayirli.recyclerviewwithdatabinding.api

import com.iebayirli.recyclerviewwithdatabinding.data.article.EmployeeResponse
import com.iebayirli.recyclerviewwithdatabinding.data.currency.RatesResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getEmployees(): Response<EmployeeResponse> = apiService.getEmployees()
    override suspend fun getLatestRates(): Response<RatesResponse> = apiService.getLatestRates()
}