

package com.app.ecom.weatherApp.helpers

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import com.app.ecom.weatherApp.R
import com.app.ecom.weatherApp.databinding.ViewLoadingAnimationBinding

class LoadingDialog(context: Context) : Dialog(context) {

    private lateinit var binding: ViewLoadingAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewLoadingAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawableResource(R.color.transparent_gray)
    }
}