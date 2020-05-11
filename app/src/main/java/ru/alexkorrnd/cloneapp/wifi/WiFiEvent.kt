package ru.alexkorrnd.cloneapp.wifi

import android.net.wifi.p2p.WifiP2pDevice

sealed class WiFiEvent {

    class DeviceInfoChanged(val wifiP2pDevice: WifiP2pDevice): WiFiEvent()

}