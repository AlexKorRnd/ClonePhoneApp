package ru.alexkorrnd.cloneapp.client.readingqrcode.connect

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import io.reactivex.Completable
import io.reactivex.Single

fun WifiP2pManager.discoverPeersAsCompletable(channel: WifiP2pManager.Channel): Completable {
    return Completable.create { emitter ->
        this.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                emitter.onComplete()
            }

            override fun onFailure(reason: Int) {
                emitter.onError(RuntimeException("Error when discoverPeers. Reason $reason"))
            }
        })
    }
}

fun WifiP2pManager.requestPeersAsSingle(channel: WifiP2pManager.Channel): Single<Collection<WifiP2pDevice>> {
    return Single.create { emitter ->
        this.requestPeers(channel) { devicesList ->
            emitter.onSuccess(devicesList.deviceList)
        }
    }
}