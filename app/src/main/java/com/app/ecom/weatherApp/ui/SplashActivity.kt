package com.app.ecom.weatherApp.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.ecom.weatherApp.BuildConfig
import com.app.ecom.weatherApp.R
import com.app.ecom.weatherApp.databinding.ActivitySplashBinding
import com.app.ecom.weatherApp.factories.SharedVMF
import com.app.ecom.weatherApp.helpers.PreferenceManager
import com.app.ecom.weatherApp.ui.mainActivity.MainActivity
import com.app.ecom.weatherApp.utils.ConnectionLiveData
import com.app.ecom.weatherApp.viewModels.SharedVM
import com.crowdfire.cfalertdialog.CFAlertDialog
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SplashActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: SharedVMF by instance()
    private lateinit var viewModel: SharedVM
    var versionCode = ""
    var versionName = ""
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var binding: ActivitySplashBinding
    private lateinit var PreferenceManager: PreferenceManager

    companion object {
        private const val SPLASH_TIME_OUT = 2300L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        /*   */
        /**CODE TO RESTRICT SCREEN RECORD AND SNAPSHOT*//*
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory)[SharedVM::class.java]

        PreferenceManager = com.app.ecom.weatherApp.helpers.PreferenceManager.instance
        versionCode = BuildConfig.VERSION_CODE.toString()
        versionName = BuildConfig.VERSION_NAME.toString()

        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this) { isNetworkAvailable ->
            isNetworkAvailable?.let {
                updateUI(it)
            }
        }
    }

    private fun updateUI(it: Boolean) {
        if (it) {
            Log.e("Network", "Available")
//            checkAppVersion(EmptyRequest())
//            setupFCM()

            hitApis()
            Handler().postDelayed({
                setupViews()

            }, SPLASH_TIME_OUT)
        } else {
            Log.e("Network", "Not Available")
            CFAlertDialog.Builder(this)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setHeaderView(R.layout.dialog_header_no_connection)
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection")
                .addButton(
                    "Dismiss", -1, -1,
                    CFAlertDialog.CFAlertActionStyle.NEGATIVE,
                    CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                .show()
        }
    }


    private fun setupViews() {
        when {
            (PreferenceManager.loggedIn) -> {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }

            else -> {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()

            }
        }
    }


    private fun hitApis() {
    }

}