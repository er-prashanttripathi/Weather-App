

package com.app.ecom.weatherApp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardUtil {

    // Show keyboard for EditText
    fun showKeyboard(view: View) {
        view.requestFocus()
        view.post {
            val inputMethod =
                view.context.getSystemService(
                    Context.INPUT_METHOD_SERVICE
                ) as InputMethodManager
            inputMethod.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    // Hide keyboard for EditText
    fun hideKeyboard(view: View) {
        val inputMethod =
            view.context.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        inputMethod.hideSoftInputFromWindow(view.windowToken, 0)
    }
}