package com.fedx.listcountries

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedx.listcountries.network.Countries
import com.fedx.listcountries.network.CountriesApi
import com.fedx.listcountries.network.Detail
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewModel: ViewModel() {
    private val _property = MutableLiveData<ArrayList<Countries>>()

    val property: LiveData<ArrayList<Countries>>
        get() = _property


    fun getCountries(){
        viewModelScope.launch {
            try {
                val detail = CountriesApi.retrofitService.getAllCountries()
                var listCountries = ArrayList<Countries>()
                detail.forEach {
                    Log.d("MyLog", "flag ${it.flags.get("png")}")
                    listCountries.add(it)
                }
                _property.value = listCountries

            }catch (e: Exception){
                Log.d("MyLog", "detail error")
            }
        }
    }
}