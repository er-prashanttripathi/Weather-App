

package com.app.ecom.weatherApp.data.remote

import java.io.IOException

/* It's a Kotlin class that extends the IOException class and takes a message as a parameter */
class ApiException(message: String) : IOException(message)

/* NoInternetException is a class that extends IOException and takes a String as a parameter. */
class NoInternetException(message: String) : IOException(message)