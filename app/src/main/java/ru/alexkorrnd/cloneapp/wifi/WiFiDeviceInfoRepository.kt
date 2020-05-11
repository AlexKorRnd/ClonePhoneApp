package ru.alexkorrnd.cloneapp.wifi

import android.content.Context
import ru.alexkorrnd.cloneapp.helpers.Converter
import ru.alexkorrnd.cloneapp.models.WiFiDeviceInfo

class WiFiDeviceInfoRepository(
    context: Context,
    private val converter: Converter
) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveDeviceInfo(device: WiFiDeviceInfo) {
        prefs.edit()
            .putString(PREFS_DEVICE_INFO_KEY, converter.toString(device))
            .apply()
    }

    fun getDeviceInfo(): WiFiDeviceInfo? {
        return prefs.getString(PREFS_DEVICE_INFO_KEY, null)?.let { deviceInfoAsString ->
            converter.fromString<WiFiDeviceInfo>(deviceInfoAsString)
        }
    }

    companion object {
        private const val PREFS_NAME = "wifi_device_info_prefs"
        private const val PREFS_DEVICE_INFO_KEY = "device_info"
    }
}