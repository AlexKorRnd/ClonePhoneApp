package ru.alexkorrnd.cloneapp.client.find

import android.net.wifi.p2p.WifiP2pDevice
import io.reactivex.Observable
import io.reactivex.internal.functions.Functions
import ru.alexkorrnd.cloneapp.models.WiFiDeviceInfo
import ru.alexkorrnd.cloneapp.wifi.WifiManagerWrapper

class FindPeerByDeviceInfo(
    private val manager: WifiManagerWrapper
) {

    fun findPeer(wifiDeviceInfo: WiFiDeviceInfo): Observable<WifiP2pDevice> {
        return manager.discoverPeers()
            .andThen(manager.requestPeers())
            .flattenAsObservable(Functions.identity())
            .filter { wifiP2pDevice -> wifiP2pDevice.deviceAddress == wifiDeviceInfo.deviceAddress }

    }

}

