package com.app.ecom.weatherApp.ui.mainActivity

import android.content.DialogInterface
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.app.ecom.weatherApp.R
import com.app.ecom.weatherApp.databinding.ActivityMainBinding
import com.app.ecom.weatherApp.factories.SharedVMF
import com.app.ecom.weatherApp.helpers.PreferenceManager
import com.app.ecom.weatherApp.utils.ConnectionLiveData
import com.app.ecom.weatherApp.viewModels.SharedVM
import com.crowdfire.cfalertdialog.CFAlertDialog
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: SharedVMF by instance()
    private lateinit var viewModel: SharedVM
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var locationManager: LocationManager? = null
    private var isHandlerRunning = true
    private lateinit var handler: Handler
    private lateinit var connectionLiveData: ConnectionLiveData
    var lat: Double = 0.0
    var lng: Double = 0.0

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
        private const val MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION = 66
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory)[SharedVM::class.java]
        preferenceManager = PreferenceManager.instance
        setupNavController()
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
//            setupHandler()
        } else {
//            closeDrawer()
            Log.e("Network", "not Available")
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

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

//        setUpBottomBar()
    }


    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }


    // Function to replace fragments in the fragment container
    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }

}