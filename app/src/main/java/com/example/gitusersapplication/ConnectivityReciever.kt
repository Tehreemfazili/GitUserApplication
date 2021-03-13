package com.example.gitusersapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService

class ConnectivityReciever :BroadcastReceiver(){
    var connectivityReceiverListener: ConnectivityReceiverListener? = null


    override fun onReceive(context: Context, intent: Intent?) {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = (activeNetwork != null
                && activeNetwork.isConnectedOrConnecting)
        if (connectivityReceiverListener != null) {
            connectivityReceiverListener!!.onNetworkConnectionChanged(isConnected)
        }
    }

    fun isConnected(context: Context): Boolean {
        val cm =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val activeNetwork = cm.activeNetworkInfo
        return (activeNetwork != null
                && activeNetwork.isConnectedOrConnecting)
    }


    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    fun setConnectivityListener(listener: ConnectivityReceiverListener) {
        ConnectivityReciever().connectivityReceiverListener = listener
    }
}