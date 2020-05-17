package ru.alexkorrnd.cloneapp.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.alexkorrnd.cloneapp.wifi.WifiManagerWrapper

val wifiModule = module {
    single {
        WifiManagerWrapper(androidContext())
    }
}