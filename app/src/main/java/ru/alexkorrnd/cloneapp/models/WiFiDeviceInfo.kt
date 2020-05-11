package ru.alexkorrnd.cloneapp.models

data class WiFiDeviceInfo(
    val deviceName: String,
    val deviceAddress: String
) {
    override fun toString(): String {
        return "$deviceName, $deviceAddress"
    }

    companion object {
    }
}