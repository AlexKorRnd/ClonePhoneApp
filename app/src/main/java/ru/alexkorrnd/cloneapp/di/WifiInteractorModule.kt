package ru.alexkorrnd.cloneapp.di

import org.koin.dsl.module
import ru.alexkorrnd.cloneapp.wifi.EventManager
import ru.alexkorrnd.cloneapp.wifi.WiFiEvent
import ru.alexkorrnd.cloneapp.wifi.WifiInteractor

val wifiInteractorModule = module {

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