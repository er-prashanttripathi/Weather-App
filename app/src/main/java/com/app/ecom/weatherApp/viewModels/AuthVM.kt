package com.app.ecom.weatherApp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidlover5842.androidUtils.Validator.StringValidator
import com.app.ecom.weatherApp.repositories.AuthRepo

class AuthVM(private val repository: AuthRepo) : ViewModel() {
    private val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val error: MutableLiveData<Error> = MutableLiveData()
    private val signInSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val terms: MutableLiveData<Boolean> = MutableLiveData()

    init {
        signInSuccess.value = false
        error.value = Error()
    }


    private fun isValidUserNameAndPassword(): Boolean {
        val error = this.error.value!!
        val isValidUserName: Boolean = StringValidator.getValidator(username.value)
            .addRule { it.length > 5 }
            .doOnEmpty { error.username = "*Username can't be empty" }
            .doOnRuleFailed { error.username = "*Username can't be less than 6 characters" }
            .doOnRulePassed { error.username = null }
            .toBool()

        val isValidPassword = StringValidator.getValidator(password.value)
            .addRule { it.length > 5 }
            .doOnEmpty { error.password = "*Password required" }
            .doOnEmptyOrRuleFailed { error.password = "*Password can't be less than 6 characters" }
            .doOnRulePassed { error.password = null }
            .toBool()
        this.error.value = error
        return isValidUserName && isValidPassword
    }

    data class Error(var username: String? = null, var password: String? = null)
}