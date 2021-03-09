package br.com.tryyourfood.listeners

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
class NetworkListener: ConnectivityManager.NetworkCallback() {

    private val isNetworkAvailable = MutableStateFlow(false)

    fun checkNetWorkAvailability(context: Context):MutableStateFlow<Boolean>{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(this)
        }

        var isConnected = false

        connectivityManager.allNetworks.forEach { network ->
            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            networkCapability?.let {
                if(it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                    isConnected = true
                    return@forEach
                }
            }
        }

        isNetworkAvailable.value = isConnected
        return isNetworkAvailable
    }


    override fun onLost(network: Network) {
        isNetworkAvailable.value = false
    }

    override fun onUnavailable() {
        isNetworkAvailable.value = false
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true
    }
}