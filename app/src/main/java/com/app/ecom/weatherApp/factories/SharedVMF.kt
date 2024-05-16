

package com.app.ecom.weatherApp.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.ecom.weatherApp.repositories.SharedRepo
import com.app.ecom.weatherApp.viewModels.SharedVM

@Suppress("UNCHECKED_CAST")

class SharedVMF(
    private val repository: SharedRepo
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SharedVM(repository) as T
    }
}