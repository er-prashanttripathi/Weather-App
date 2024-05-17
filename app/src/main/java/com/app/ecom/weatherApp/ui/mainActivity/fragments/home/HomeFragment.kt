package com.app.ecom.weatherApp.ui.mainActivity.fragments.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.ecom.weatherApp.R
import com.app.ecom.weatherApp.databinding.FragmentHomeBinding
import com.app.ecom.weatherApp.factories.SharedVMF
import com.app.ecom.weatherApp.helpers.PreferenceManager
import com.app.ecom.weatherApp.models.weather.WeatherRes
import com.app.ecom.weatherApp.utils.GPSTracker
import com.app.ecom.weatherApp.utils.LoadingUtils
import com.app.ecom.weatherApp.utils.toast
import com.app.ecom.weatherApp.viewModels.SharedVM
import com.crowdfire.cfalertdialog.CFAlertDialog
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.sports.battle.utils.onDebouncedListener
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder


class HomeFragment : Fragment(), KodeinAware, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: FragmentHomeBinding
    override val kodeinContext = kcontext<Fragment>(this)
    override val kodein: Kodein by kodein()
    private val factory: SharedVMF by instance()
    private lateinit var viewModel: SharedVM
    private lateinit var preferenceManager: PreferenceManager
    val ApiKeyWeather = "a9933cf8b239b613efa806adc3c8f79a"
    private var errorDialogShown = false
    private var currentAddress = ""
//    private lateinit var nMap: GoogleMap
    var zipCode = ""
    var locality = ""
    var latitude = 0.0
    var longitude = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                showExitDialog()

            }
        })
        // Find current location and initiate weather API call
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        if (isLocationPermissionGranted()) {
            findCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferenceManager = com.app.ecom.weatherApp.helpers.PreferenceManager.instance
        viewModel = ViewModelProvider(requireActivity(), factory)[SharedVM::class.java]
        setupclisklistener()
        setupViews()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupViews() {
// Initialize swipeRefreshLayout and set listener
        binding.swipeLayout.setOnRefreshListener(this)

        if (isLocationPermissionGranted()) {
            // Proceed with location retrieval
            findCurrentLocation()
            setupCurrentLocation()
        }
        else {
            // Request permissions from the user
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupCurrentLocation() {
        try {
            val gpsTracker = GPSTracker(requireActivity())

            if (gpsTracker.canGetLocation()) {
                latitude = gpsTracker.latitude
                longitude = gpsTracker.longitude
                locality = gpsTracker.getLocality(requireContext()) // Pass context here
//                showToast("${latitude},${longitude}")

                if (latitude != null && longitude != null) {
                    // Perform weather API call or any other operation with location
                    weatherApi(latitude, longitude, ApiKeyWeather, zipCode, "metric")

                    // Optionally use locality for UI or other purposes
                    Log.d("Location", "Locality: $locality")
                } else {
                    // Handle situation where latitude or longitude is null
                    Log.e("Location", "Latitude or Longitude is null")
                }
            } else {
                // Location cannot be retrieved, show settings alert
                gpsTracker.showSettingsAlert()
            }
        } catch (e: Exception) {
            // Handle any other exceptions
            Log.e("Error", "$e")
            GPSTracker(requireActivity()).showSettingsAlert()
            LoadingUtils.hideDialog()
        }
    }

  /*  @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        *//**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play Stations is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play Stations and returned to the app.
         *//*
        *//**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play Stations is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play Stations and returned to the app.
         *//*
        nMap = googleMap
        nMap.clear()

        val userCurrentLocation = LatLng(latitude, longitude)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(userCurrentLocation))
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(latitude, longitude),
                13f
            )
        )
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        googleMap.uiSettings.isZoomControlsEnabled = false

        // user location
        googleMap.addMarker(
            MarkerOptions().position(LatLng(latitude, longitude))
                .title("Your Location !")
                .snippet("" + currentAddress)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        )

    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupclisklistener() {
        binding.apply {
            btnSearch.onDebouncedListener {
                openAddLocationSheet("Sheet opened")

            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun openAddLocationSheet(type: String) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_address)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        val btnClose = bottomSheetDialog.findViewById<MaterialButton>(R.id.btn_close)
        val btnSearchLoc = bottomSheetDialog.findViewById<MaterialButton>(R.id.btn_search)
        val etAddress = bottomSheetDialog.findViewById<TextInputEditText>(R.id.et_address)
        btnSearchLoc?.setOnClickListener {
            if (!etAddress?.text.toString().isNullOrEmpty()) {
                context?.toast("Location Changed !!")
                weatherApi(
                    latitude,
                    longitude,
                    ApiKeyWeather,
                    "${etAddress!!.text.toString()},in",
                    "metric"
                )//in is country code
                bottomSheetDialog.dismiss()
            } else {
                etAddress?.error = "Enter Zip Code !"
            }
        }

        btnClose?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), "${message}", Toast.LENGTH_SHORT).show()
    }
    private fun errorDialog(errMsg: String) {
        Log.e("Error", "not Available")
        CFAlertDialog.Builder(requireContext())
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setHeaderView(R.layout.dialog_header_api_failed)
            .setTitle("No Data Found!!")
            .setMessage("${errMsg}")
            .addButton(
                "Dismiss", -1, -1,
                CFAlertDialog.CFAlertActionStyle.NEGATIVE,
                CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
            ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            .show()

    }


    private fun showExitDialog() {
        CFAlertDialog.Builder(context)
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setHeaderView(R.layout.dialog_header_logout)
            .setTitle("Exit App !!")
            .setMessage("Are you sure you want to exit from this app ?")
            .addButton(
                "Yes", -1, -1,
                CFAlertDialog.CFAlertActionStyle.POSITIVE,
                CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
            ) { dialog: DialogInterface, _: Int ->
                requireActivity().finishAffinity()
                dialog.dismiss()
            }
            .addButton(
                "No", -1, -1,
                CFAlertDialog.CFAlertActionStyle.NEGATIVE,
                CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
            ) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun weatherApi(lat: Double, lon: Double, appid: String, zip: String, units: String) {
        // Call the API function
        viewModel.weatherApi(lat, lon, appid, "${zip}", units)

        // Observe the LiveData for response
        viewModel.weatherApi.observe(viewLifecycleOwner, Observer { response ->
            // Handle the response here
            if (response != null) {
                binding.swipeLayout.isRefreshing = false //to stop loader
                Log.d("locationApi ", response.toString())
                val jsonString = response
                try {
                    val gson = Gson()
                    val apiResponse =
                        gson.fromJson(jsonString, WeatherRes::class.java)

                    Log.d("locationApi ", "findCurrentLocation: ${apiResponse.toString()}")
                    binding.apply {
                        tvLocation.text = "" + apiResponse.name.toString()
                        tvStatus.text = "" + apiResponse.weather[0].main.toString()
                        tvTemperature.text = "" + apiResponse.main.temp.toString() + " °C"
                        tvTempMax.text = "" + apiResponse.main.temp_max.toString() + " °C"
                        tvTempMin.text = "" + apiResponse.main.temp_min.toString() + " °C"
                        tvTempFeelLike.text = "" + apiResponse.main.feels_like.toString() + " °C"
                        tvSunrise.text =
                            "" + formatTimestampToTime(apiResponse.sys.sunrise.toLong())
                        tvSunset.text = "" + formatTimestampToTime(apiResponse.sys.sunset.toLong())
                        tvWindSpeed.text = "" + apiResponse.wind.speed.toString() + " m/s"
                        tvPressure.text = "" + apiResponse.main.pressure.toString() + " hPa"
                        tvHumidity.text = "" + apiResponse.main.humidity.toString() + " %"
                    }
                } catch (e: Exception) {
                    Log.e("Error", "locationApi: ${e}")
                    if (!errorDialogShown) {
                        errorDialog("${e}")
                        errorDialogShown = true
                    }
                }


            } else {
                if (!errorDialogShown) {
                    errorDialog("No Data Found")
                    errorDialogShown = true
                }
                errorDialog("No Data Found")
                Log.e("locationApi", "Response is null")
            }


        })
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTimestampToTime(timestamp: Long): String {
        val dateTime = LocalDateTime.ofEpochSecond(timestamp, 0, java.time.ZoneOffset.UTC)

        val formatter = DateTimeFormatterBuilder()
            .appendPattern("hh:mm:ss a") // 'a' for AM/PM
            .toFormatter()

        return dateTime.format(formatter)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, proceed with location retrieval
                setupCurrentLocation()
            } else {
                // Location permission denied, handle accordingly (e.g., show a message)
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun findCurrentLocation() {
        // Check for location permission
        if (isLocationPermissionGranted()) {
            // Initialize GPS Tracker to get current location
            val gpsTracker = GPSTracker(requireContext())
            if (gpsTracker.canGetLocation()) {
                latitude = gpsTracker.latitude
                longitude = gpsTracker.longitude
                //                showToast("${latitude},${longitude}")
                weatherApi(
                    latitude,
                    longitude,
                    ApiKeyWeather,
                    zipCode,
                    "metric"
                ) //Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit
            } else {
                // Handle case where location could not be retrieved
                showToast("Unable to get current location")
            }
        } else {
            // Handle case where location permission is not granted
            showToast("Location permission not granted")
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (isLocationPermissionGranted()) {
                findCurrentLocation()
                showToast("Location Updated")
                weatherApi(latitude, longitude, ApiKeyWeather, zipCode, "metric")

            } else {
//                showToast("swipe not worked")

                // Request location permission
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }, 1000)
    }

}

