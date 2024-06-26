

package com.sports.battle.utils

import android.view.View

inline fun View.onDebouncedListener(
    delayInClick: Long = 5000L,
    crossinline listener: (View) -> Unit
) {
    val enableAgain = Runnable { isEnabled = true }

    setOnClickListener {
        if (isEnabled) {
            isEnabled = false
            postDelayed(enableAgain, delayInClick)
            listener(it)
        }
    }
}