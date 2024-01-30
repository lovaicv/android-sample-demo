package com.my.recyclerviewwithdatabinding.scene.rates

//import RateRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.recyclerviewwithdatabinding.database.Currency
import com.my.recyclerviewwithdatabinding.database.CurrencyRepository
import com.my.recyclerviewwithdatabinding.other.Resource
import com.my.recyclerviewwithdatabinding.repository.MainRepository
import com.my.recyclerviewwithdatabinding.utils.ConnectionChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val currencyRepository: CurrencyRepository,
    private val connectionChecker: ConnectionChecker,
) : ViewModel() {

    private val _res = MutableLiveData<Resource<List<Currency>>>()
    val res: LiveData<Resource<List<Currency>>> get() = _res
    private val _isRefresh = MutableLiveData<Boolean>()
    val isRefresh: LiveData<Boolean> get() = _isRefresh

    fun setRefresh(isRefresh: Boolean) {
        _isRefresh.value = isRefresh
    }

    init {
        getCurrencies()
    }

    fun getCurrencies() = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        Log.d("check", "getLatestRates start")
        try {
            if (connectionChecker.isNetworkAvailable()) {
                mainRepository.getLatestRates().let {
                    Log.d("check", "getLatestRates status $it")
                    if (it.isSuccessful) {
                        it.body().let {
                            Log.d("check", "res body $it")
                            Log.d("check", "delete all entries")
                            currencyRepository.deleteAll()
                            it?.rates?.forEach {
                                currencyRepository.insertCurrency(
                                    Currency(
                                        currencySymbol = it.key,
                                        exchangeRate = it.value,
                                        exchangeAmount = 1.0
                                    )
                                )
                            }
//                    currencyRepository.getAllCurrencies().forEach {
//                        Log.d(
//                            "check",
//                            "this is from db " + it.currencySymbol + ">" + it.exchangeRate + ">" + it.exchangeAmount
//                        )
//                    }
                        }
//                        _res.postValue(Resource.success(it.body()?.rates?.let { it1 -> toList(it1) }))
                        getCurrenciesFromDB()
                    } else {
                        _res.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                getCurrenciesFromDB()
                _res.postValue(Resource.error("no connection available", null))
            }
        } catch (exception: Exception) {
            getCurrenciesFromDB()
            _res.postValue(Resource.error(exception.toString(), null))
        }
    }

    private fun getCurrenciesFromDB() {
        viewModelScope.launch {
            val currencyList = currencyRepository.getAllCurrencies()
            if (currencyList.isNotEmpty()) {
                _res.postValue(Resource.success(currencyList))
            }
        }
    }

//    private fun toList(rates: Map<String, Double>): MutableList<Currency> {
//        val list = mutableListOf<Currency>();
//        rates.forEach {
////            Log.d("check", it.key + it.value)
//            list.add(
//                Currency(
//                    currencySymbol = it.key,
//                    exchangeRate = it.value,
//                    exchangeAmount = 1.0
//                )
//            );
//        }
//        return list
//    }
}
