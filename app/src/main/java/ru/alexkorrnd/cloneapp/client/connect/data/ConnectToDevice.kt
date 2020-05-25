package ru.alexkorrnd.cloneapp.client.connect.data

import android.net.wifi.p2p.WifiP2pDevice
import io.reactivex.Completable
import ru.alexkorrnd.cloneapp.client.find.connect
import ru.alexkorrnd.cloneapp.wifi.WifiManagerWrapper

class ConnectToDevice(
    private val manager: WifiManagerWrapper,
    private val wiFiConfigCreator: WiFiConfigCreator
) {

    fun connect(deviceInfo: WifiP2pDevice): Completable {
        return manager.connect(wiFiConfigCreator.create(deviceInfo))
    }
}