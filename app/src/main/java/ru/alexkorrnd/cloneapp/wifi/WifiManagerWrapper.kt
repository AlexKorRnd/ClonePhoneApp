package ru.alexkorrnd.cloneapp.wifi

import android.content.Context
import android.net.wifi.p2p.WifiP2pManager
import android.os.Looper
import androidx.core.content.ContextCompat

class WifiManagerWrapper(
    private val context: Context
) {

    val manager by lazy { ContextCompat.getSystemService<WifiP2pManager>(context, WifiP2pManager::class.java)!! }

    val channel by lazy { manager.initialize(context, Looper.getMainLooper(), null) }

}