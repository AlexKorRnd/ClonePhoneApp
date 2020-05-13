package ru.alexkorrnd.cloneapp.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.alexkorrnd.cloneapp.wifi.*

val wifiModule = module {

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

    single {
        EventManager<WiFiEvent>()
    }

    single {
        WifiInteractor(
            eventManager = get(),
            deviceInfoSaver = get()
        )
    }
}