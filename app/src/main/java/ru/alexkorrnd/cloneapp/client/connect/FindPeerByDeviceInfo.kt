package ru.alexkorrnd.cloneapp.client.connect

import android.net.wifi.p2p.WifiP2pDevice
import io.reactivex.Single
import io.reactivex.internal.functions.Functions
import ru.alexkorrnd.cloneapp.helpers.Converter
import ru.alexkorrnd.cloneapp.models.WiFiDeviceInfo
import ru.alexkorrnd.cloneapp.wifi.WifiManagerWrapper

class FindPeerByDeviceInfo(
    private val manager: WifiManagerWrapper,
    private val converter: Converter
) {

    fun findPeer(wifiDevInfoAsString: String): Single<WifiP2pDevice> {
        return Single.fromCallable {
            converter.fromString<WiFiDeviceInfo>(wifiDevInfoAsString)
        }.flatMap { wifiDeviceInfo -> findPeer(wifiDeviceInfo) }
    }

    fun findPeer(wifiDeviceInfo: WiFiDeviceInfo): Single<WifiP2pDevice> {
        return manager.discoverPeers()
            .andThen(manager.requestPeers())
            .flattenAsObservable(Functions.identity())
            .filter { wifiP2pDevice ->  wifiP2pDevice.deviceName == wifiDeviceInfo.deviceName && wifiP2pDevice.deviceAddress == wifiDeviceInfo.deviceAddress }
            .firstOrError()
    }

}

