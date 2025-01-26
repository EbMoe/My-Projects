package com.alpha.eventplanner

import android.content.Context
import android.content.Intent
import android.content.BroadcastReceiver
import android.net.ConnectivityManager
import android.util.Log

class ConnectivityReceiver(private val onNetworkAvailable: () -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (isOnline(context)) {
            Log.d("ConnectivityReceiver", "Device is back online, triggering sync.")
            onNetworkAvailable()  // Call the callback to sync data in the fragment/activity
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}
