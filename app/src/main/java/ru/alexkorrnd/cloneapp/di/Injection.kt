package ru.alexkorrnd.cloneapp.di

import android.content.Context
import com.google.gson.Gson
import ru.alexkorrnd.cloneapp.generatingqrcode.ShowQrCodePresenter
import ru.alexkorrnd.cloneapp.generatingqrcode.data.QrCodeGenerator
import ru.alexkorrnd.cloneapp.generatingqrcode.data.QrCodeView
import ru.alexkorrnd.cloneapp.helpers.Converter
import ru.alexkorrnd.cloneapp.wifi.*

object Injection {

    lateinit var context: Context

    fun provideGson() = Gson()

    fun provideConverter() = Converter(
        gson = provideGson()
    )

    fun provideWiFiDeviceInfoRepository() = WiFiDeviceInfoRepository(
        context = context,
        converter = provideConverter()
    )

    fun provideWifiDeviceInfoLoader() = WiFiDeviceInfoLoader(
        wiFiDeviceInfoRepository = provideWiFiDeviceInfoRepository()
    )

    fun provideWiFiDeviceInfoSaver() = WiFiDeviceInfoSaver(
        deviceInfoRepository = provideWiFiDeviceInfoRepository()
    )

    fun provideQrCodeGenerator() = QrCodeGenerator()

    fun provideShowQrCodePresenter(view: QrCodeView) = ShowQrCodePresenter(
        wifiDeviceInfoLoader = provideWifiDeviceInfoLoader(),
        converter = provideConverter(),
        qrCodeGenerator = provideQrCodeGenerator(),
        view = view
    )

    fun <Type> provideEventManager() = EventManager<Type>()

    fun provideWifiInteractor() = WifiInteractor(
        eventManager = provideEventManager(),
        deviceInfoSaver = provideWiFiDeviceInfoSaver()
    )

}