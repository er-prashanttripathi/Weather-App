

package com.app.ecom.weatherApp.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.app.ecom.weatherApp.R

class AlertDialogUtil {
    /* This is an extension function. It is used to extend the functionality of a class. */
    fun Context.showAlertDialog(
        title: String? = null,
        message: String,
        posBtnText: String? = null,
        negBtnText: String? = null,
        showNegBtn: Boolean = true,
        callback: () -> Unit
    ) {
        /* Creating an alert dialog with the given parameters. */
        AlertDialog.Builder(this, R.style.AlertDialogTheme).also {
            it.setTitle(title ?: getString(R.string.alert))
            it.setMessage(message)
            it.setPositiveButton(posBtnText ?: getString(R.string.yes)) { _, _ ->
                callback()
            }
            if (showNegBtn) {
                it.setNegativeButton(negBtnText ?: getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
            }
        }.create().show()
    }

}