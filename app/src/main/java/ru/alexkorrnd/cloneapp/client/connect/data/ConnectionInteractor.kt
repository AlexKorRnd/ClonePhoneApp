package ru.alexkorrnd.cloneapp.client.connect.data

import io.reactivex.Completable
import io.reactivex.Observable
import ru.alexkorrnd.cloneapp.client.find.FindPeerByDeviceInfo
import ru.alexkorrnd.cloneapp.helpers.Converter
import ru.alexkorrnd.cloneapp.models.WiFiDeviceInfo
import ru.alexkorrnd.cloneapp.wifi.EventManager
import ru.alexkorrnd.cloneapp.wifi.WiFiEvent
import timber.log.Timber

class ConnectionInteractor(
    private val converter: Converter,
    private val eventManager: EventManager<WiFiEvent>,
    private val findPeerByDeviceInfo: FindPeerByDeviceInfo,
    private val connectToDevice: ConnectToDevice
) {

    fun connect(wifiDevInfoAsString: String): Completable {
        val deviceInfo = converter.fromString<WiFiDeviceInfo>(wifiDevInfoAsString)
        return Observable.merge(
            eventManager.wifiEvents.filter { event -> event is WiFiEvent.PeersChanged }
                .flatMap {
                    Timber.i("received PeersChanged event!!!")
                    findPeerByDeviceInfo.findPeer(deviceInfo)
                },
            findPeerByDeviceInfo.findPeer(deviceInfo)
        ).firstElement()
            .flatMapCompletable { connectToDevice.connect(it) }
    }
}