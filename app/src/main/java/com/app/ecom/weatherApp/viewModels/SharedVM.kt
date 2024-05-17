package com.app.ecom.weatherApp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.ecom.weatherApp.helpers.Coroutines

import com.app.ecom.weatherApp.repositories.SharedRepo

class SharedVM(private val repository: SharedRepo) : ViewModel() {


    val error: MutableLiveData<Error> = MutableLiveData()

    init {
        error.value = Error()
    }


    var weatherApi = MutableLiveData<String>()
    fun weatherApi(lat: Double, lon: Double, appid: String, zip: String, units: String) {
        Coroutines.io {
            val response = repository.weatherApi(lat, lon, appid, zip, units)
            weatherApi.postValue(response)
        }
    }


    data class Error(var error: String? = null)
}
