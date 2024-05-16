
package com.app.ecom.weatherApp.utils


import androidx.navigation.NavOptions
import com.app.ecom.weatherApp.R

fun getNavOptions(): NavOptions? {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_from_right)
        .setExitAnim(R.anim.slide_out_to_right)
        .setPopEnterAnim(R.anim.slide_in_from_left)
        .setPopExitAnim(R.anim.slide_out_left)
        .build()
}