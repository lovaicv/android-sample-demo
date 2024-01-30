package com.my.recyclerviewwithdatabinding.data.article

import com.google.gson.annotations.SerializedName

data class EmployeeResponse(
    @SerializedName("products") var products: ArrayList<Employee> = arrayListOf(),
    @SerializedName("total") var total: Int? = null,
    @SerializedName("skip") var skip: Int? = null,
    @SerializedName("limit") var limit: Int? = null
)
