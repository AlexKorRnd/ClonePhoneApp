package ru.alexkorrnd.cloneapp.wifi

import ru.alexkorrnd.cloneapp.models.toWifiDeviceInfo

class WifiInteractor(
    private val eventManager: EventManager<WiFiEvent>,
    private val deviceInfoSaver: WiFiDeviceInfoSaver
) {

    fun processEvent(event: WiFiEvent) {
        return when(event) {
            is WiFiEvent.DeviceInfoChanged -> {
                val deviceInfo = event.wifiP2pDevice
                eventManager.sendEvent(WiFiEvent.DeviceInfoChanged(deviceInfo))
                deviceInfoSaver.save(deviceInfo.toWifiDeviceInfo())
            }
        }
    }

}