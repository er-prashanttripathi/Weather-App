package com.app.ecom.weatherApp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.ecom.weatherApp.helpers.Coroutines

import com.app.ecom.weatherApp.repositories.SharedRepo

class SharedVM(private val repository: SharedRepo) : ViewModel() {

    var latitude: MutableLiveData<Double> = MutableLiveData()
    var logitude: MutableLiveData<Double> = MutableLiveData()
    var fullAddress: MutableLiveData<String> = MutableLiveData()
    val error: MutableLiveData<Error> = MutableLiveData()

    init {
        error.value = Error()
    }

    fun setLatitude(latitude: Double) {
        this.latitude.postValue(latitude)
    }

    fun setLongitude(longitude: Double) {
        logitude.postValue(longitude)
    }

    fun setFullAddress(fullAddress: String) {
        this.fullAddress.postValue(fullAddress)
    }


    var listclasssubject = MutableLiveData<String>()
    fun listclasssubject(examBoardClassId: Int) {
        Coroutines.io {
            val response = repository.listclasssubject(examBoardClassId)
            listclasssubject.postValue(response)
        }
    }



    var checkclspurchased = MutableLiveData<String>()
    fun checkclspurchased(ex_brd_cls: Int, ex_brd: Int, user: Int, response: (String) -> Unit) {
        Coroutines.main {
            val newResponse = repository.checkclspurchased(ex_brd_cls, ex_brd, user)
            response.invoke(newResponse)

//            checkclspurchased.postValue(response)
        }
    }

    var weatherApi = MutableLiveData<String>()
    fun weatherApi(lat: Double, lon: Double, appid: String,zip: String, units: String) {
        Coroutines.io {
            val response = repository.weatherApi(lat,lon,appid,zip,units)
            weatherApi.postValue(response)
        }
    }


    data class Error(var error: String? = null)
}
