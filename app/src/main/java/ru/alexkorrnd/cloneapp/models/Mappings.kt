package ru.alexkorrnd.cloneapp.models

import android.net.wifi.p2p.WifiP2pDevice

fun WifiP2pDevice.toWifiDeviceInfo() = WiFiDeviceInfo(
    deviceName = this.deviceName,
    deviceAddress = this.deviceAddress
)