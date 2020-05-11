package ru.alexkorrnd.cloneapp.wifi

import ru.alexkorrnd.cloneapp.models.WiFiDeviceInfo

class WiFiDeviceInfoLoader(
    private val wiFiDeviceInfoRepository: WiFiDeviceInfoRepository
) {

    fun load(): WiFiDeviceInfo? {
        return wiFiDeviceInfoRepository.getDeviceInfo()
    }
}