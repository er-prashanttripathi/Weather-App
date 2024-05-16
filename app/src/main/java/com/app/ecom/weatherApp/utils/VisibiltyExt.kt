

package com.app.ecom.weatherApp.utils

import android.view.View

class VisibiltyExt {
    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    fun View.toggleVisibility(): View {
        visibility = if (visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
        return this
    }
}