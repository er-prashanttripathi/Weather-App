

package com.app.ecom.weatherApp.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.ecom.weatherApp.repositories.AuthRepo
import com.app.ecom.weatherApp.viewModels.AuthVM

@Suppress("UNCHECKED_CAST")

class AuthVMF(
    private val repository: AuthRepo
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthVM(repository) as T
    }
}