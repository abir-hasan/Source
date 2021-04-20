package com.example.abir.source

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.abir.source.utils.logDebug
import com.example.abir.source.utils.logError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "onCreate() called ".logDebug()
        connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        setupNetworkStatusListener()
    }

    private var isOnline = false

    private lateinit var connectivityManager: ConnectivityManager

    private lateinit var callback: ConnectivityManager.NetworkCallback

    private fun setupNetworkStatusListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val request = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()
            callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    isOnline = checkNetworkState(network)
                    lifecycleScope.launch(Dispatchers.Main) { onNetworkStatusChange(isOnline) }
                    "onAvailable() called with: network = $network".logDebug()
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    isOnline = checkNetworkState(network)
                    lifecycleScope.launch(Dispatchers.Main) { onNetworkStatusChange(isOnline) }
                    "onLost() called with: network = $network".logError()
                }
            }
            /**
             * This wont cause memory leak as
             * The app continues to receive callbacks until either the app exits or
             * it calls unregisterNetworkCallback()
             * */
            connectivityManager.registerNetworkCallback(request, callback)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun checkNetworkState(activeNetwork: Network?): Boolean {
        val networkCapability = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapability?.let {
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } ?: false
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy() called".logDebug()
        connectivityManager.unregisterNetworkCallback(callback)
    }

    /**
     * Override this method to get device online status events
     */
    open fun onNetworkStatusChange(isOnline: Boolean) {

    }

}