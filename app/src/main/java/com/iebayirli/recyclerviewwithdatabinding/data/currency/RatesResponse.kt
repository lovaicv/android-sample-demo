package com.iebayirli.recyclerviewwithdatabinding.data.currency

import com.google.gson.annotations.SerializedName

data class RatesResponse(
    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("timestamp") var timestamp: Int? = null,
    @SerializedName("base") var base: String? = null,
    @SerializedName("date") var date: String? = null,
//    @SerializedName("rates") var rates: Rates? = Rates(),
    @SerializedName("rates") var rates: Map<String, Double>

)