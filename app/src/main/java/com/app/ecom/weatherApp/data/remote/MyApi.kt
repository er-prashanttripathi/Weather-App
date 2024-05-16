package com.app.ecom.weatherApp.data.remote

import com.app.ecom.weatherApp.helpers.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MyApi {


    @GET("api/v1/list_class_subject")
    suspend fun listclasssubject(
        @Query("ex_brd_cls") examBoardClassId: Int
    ): Response<String>


    @GET("api/v1/check_cls_purchased")
    suspend fun checkclspurchased(
        @Query("ex_brd_cls") ex_brd_cls: Int,
        @Query("ex_brd") ex_brd: Int,
        @Query("user") user: Int
    ): Response<String>
    @GET("data/2.5/weather")
    suspend fun weatherApi(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("zip") zip: String,
        @Query("units") units: String
    ): Response<String>


    /* Creating a singleton object of MyApi. */
    companion object {
        /**
         * It creates a Retrofit instance.
         *
         * @param networkConnectionInterceptors This is the interceptor that we created in the previous
         * step.
         * @return Retrofit.Builder()
         */
        operator fun invoke(
            networkConnectionInterceptors: NetworkConnectionInterceptors
        ): MyApi {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val headerInterceptor = CustomInterceptor()

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptors)
                .addInterceptor(headerInterceptor)
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

    /* > This class is an interceptor that intercepts the request and adds a custom header to it */
    /*    class CustomInterceptor : Interceptor {
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val request = chain.request().newBuilder()
                    .build()
                return chain.proceed(request)
            }
        }*/
    class CustomInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request()

            // Check if the request needs headers based on URL or method
            val modifiedRequest = if (request.url.toString().contains("with-header")) {
                // Add headers for API calls that require headers
                request.newBuilder()
                    .addHeader("Authorization", "Bearer your_token_here")
                    .build()
            } else {
                // No headers needed for other API calls
                request
            }

            return chain.proceed(modifiedRequest)
        }
    }
}