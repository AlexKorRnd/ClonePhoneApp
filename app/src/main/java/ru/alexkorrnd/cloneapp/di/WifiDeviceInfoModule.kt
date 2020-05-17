package ru.alexkorrnd.cloneapp.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.alexkorrnd.cloneapp.wifi.WiFiDeviceInfoLoader
import ru.alexkorrnd.cloneapp.wifi.WiFiDeviceInfoRepository
import ru.alexkorrnd.cloneapp.wifi.WiFiDeviceInfoSaver

val wifiDeviceInfoModule = module {
    single {
        WiFiDeviceInfoRepository(
            context = androidContext(),
            converter = get()
        )
    }

    single {
        WiFiDeviceInfoLoader(
            wiFiDeviceInfoRepository = get()
        )
    }

    single {
        WiFiDeviceInfoSaver(
            deviceInfoRepository = get()
        )
    }
}