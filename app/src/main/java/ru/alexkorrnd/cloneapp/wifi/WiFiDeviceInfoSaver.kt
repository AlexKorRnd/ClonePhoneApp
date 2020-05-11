package ru.alexkorrnd.cloneapp.wifi

import ru.alexkorrnd.cloneapp.models.WiFiDeviceInfo

class WiFiDeviceInfoSaver(
    private val deviceInfoRepository: WiFiDeviceInfoRepository
) {

    fun save(deviceInfo: WiFiDeviceInfo) {
        deviceInfoRepository.saveDeviceInfo(deviceInfo)
    }

}