package ru.alexkorrnd.cloneapp.client.find

import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import io.reactivex.Completable
import io.reactivex.Single
import ru.alexkorrnd.cloneapp.wifi.WifiManagerWrapper
import timber.log.Timber

fun WifiManagerWrapper.discoverPeers(): Completable {
    Timber.v("discoverPeers!!!")
    return Completable.create { emitter ->
        this.manager.
        this.manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                emitter.onComplete()
            }

            override fun onFailure(reason: Int) {
                emitter.onError(RuntimeException("Error when discoverPeers. Reason $reason"))
            }
        })
    }
}

fun WifiManagerWrapper.requestPeers(): Single<List<WifiP2pDevice>> {
    Timber.v("requestPeers")
    return Single.create { emitter ->
        this.manager.requestPeers(channel) { devicesList ->
            Timber.d("receivedDevices!!! count = ${devicesList.deviceList.size}")
            devicesList.deviceList.forEach {
                Timber.i("deviceName = ${it.deviceName}, deviceAddress = ${it.deviceAddress}")
            }
            emitter.onSuccess(mutableListOf<WifiP2pDevice>().apply {
                addAll(devicesList.deviceList)
            }.toList())
        }
    }
}

fun WifiManagerWrapper.connect(config: WifiP2pConfig) : Completable {
    return Completable.create { emitter ->
        this.manager.connect(channel, config, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                emitter.onComplete()
            }

            override fun onFailure(reason: Int) {
                emitter.onError(IllegalStateException("Connection failure! Reason = $reason"))
            }
        })
    }
}