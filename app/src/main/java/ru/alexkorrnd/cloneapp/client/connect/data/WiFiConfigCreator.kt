package ru.alexkorrnd.cloneapp.client.connect.data

import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice

class WiFiConfigCreator {

    fun create(deviceInfo: WifiP2pDevice): WifiP2pConfig {
        val config = WifiP2pConfig()
        config.deviceAddress = deviceInfo.deviceAddress
        config.wps.setup = WpsInfo.PBC
        return config
    }

}