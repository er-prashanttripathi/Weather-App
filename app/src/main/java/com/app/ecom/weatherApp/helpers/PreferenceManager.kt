
package com.app.ecom.weatherApp.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class PreferenceManager private constructor(context: Context) {
    var sharedPreferences: SharedPreferences
    var context: Context
    var editor: SharedPreferences.Editor
    fun clear() {
        editor.clear().commit()
    }



    var userid: String?
        get() = sharedPreferences.getString("userid", "")
        set(userid) {
            editor.putString("userid", userid)
            editor.commit()
        }
    var userName: String?
        get() = sharedPreferences.getString("userName", "")
        set(userName) {
            editor.putString("userName", userName)
            editor.commit()
        }

    var accessToken: String?
        get() = sharedPreferences.getString("accessToken", "")
        set(accessToken) {
            editor.putString("accessToken", accessToken)
            editor.commit()
        }




    var phoneNumber: String?
        get() = sharedPreferences.getString("phoneNumber", "")
        set(phoneNumber) {
            editor.putString("phoneNumber", phoneNumber)
            editor.commit()
        }


    var address: String?
        get() = sharedPreferences.getString("Address", "")
        set(setAddress) {
            editor.putString("Address", setAddress)
            editor.commit()
        }

    var token: String?
        get() = sharedPreferences.getString("Token", "")
        set(Token) {
            editor.putString("Token", Token)
            editor.commit()
        }

    var loggedIn: Boolean
        get() = sharedPreferences.getBoolean("loggedIn", false)
        set(loggedIn) {
            editor.putBoolean("loggedIn", loggedIn)
            editor.commit()
        }
    var otp: String?
        get() = sharedPreferences.getString("otp", "")
        set(Token) {
            editor.putString("otp", Token)
            editor.commit()
        }


    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        lateinit var instance: PreferenceManager
            private set

        fun init(context: Context) {
            instance = PreferenceManager(context)
        }
    }

    init {
        sharedPreferences = context.getSharedPreferences("tituservice", Context.MODE_PRIVATE)
        this.context = context
        editor = sharedPreferences.edit()
    }
}