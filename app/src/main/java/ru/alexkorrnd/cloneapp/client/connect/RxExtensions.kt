package ru.alexkorrnd.cloneapp.client.connect

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import io.reactivex.Completable
import io.reactivex.Single
import ru.alexkorrnd.cloneapp.wifi.WifiManagerWrapper

fun WifiManagerWrapper.discoverPeers(): Completable {
    return Completable.create { emitter ->
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

fun WifiManagerWrapper.requestPeers(): Single<Collection<WifiP2pDevice>> {
    return Single.create { emitter ->
        this.manager.requestPeers(channel) { devicesList ->
            emitter.onSuccess(devicesList.deviceList)
        }
    }
}