package ru.alexkorrnd.cloneapp.generatingqrcode

import ru.alexkorrnd.cloneapp.generatingqrcode.data.QrCodeGenerator
import ru.alexkorrnd.cloneapp.generatingqrcode.data.QrCodeView
import ru.alexkorrnd.cloneapp.helpers.Converter
import ru.alexkorrnd.cloneapp.wifi.WiFiDeviceInfoLoader
import timber.log.Timber

class ShowQrCodePresenter(
    private val wifiDeviceInfoLoader: WiFiDeviceInfoLoader,
    private val converter: Converter,
    private val qrCodeGenerator: QrCodeGenerator,
    private val view: QrCodeView
) {

    fun loadQrCode() {
        Timber.i("view = $view")
        try {
            val wiFiDeviceInfo = wifiDeviceInfoLoader.load()!!
            val infoAsString = converter.toString(wiFiDeviceInfo)
            val bitmap = qrCodeGenerator.generate(infoAsString)
            view.showBitmap(bitmap)
        } catch (e: Exception) {
            Timber.e(e)
        }

    }
}