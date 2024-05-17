package com.app.ecom.weatherApp.repositories

import android.util.Log
import com.app.ecom.weatherApp.data.remote.MyApi
import com.app.ecom.weatherApp.data.remote.SafeApiRequest

class SharedRepo(
    private val api: MyApi
) : SafeApiRequest() {


    suspend fun listclasssubject(examBoardClassId: Int): String {
        var response = ""
        try {
            response = apiRequest { api.listclasssubject(examBoardClassId) }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("examboards_ex", e.toString())
        }
        return response
    }


    suspend fun checkclspurchased(ex_brd_cls: Int, ex_brd: Int, user: Int): String {
        var response = ""
        try {
            response = apiRequest { api.checkclspurchased(ex_brd_cls, ex_brd, user) }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("checkclspurchased", e.toString())
        }
        return response
    }


    suspend fun weatherApi(
        lat: Double,
        lon: Double,
        appid: String,
        zip: String,
        units: String
    ): String {
        var response = ""
        try {
            response = apiRequest { api.weatherApi(lat, lon, appid, zip, units) }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("weatherApi", e.toString())
        }
        return response
    }


}