package ru.alexkorrnd.cloneapp.di

import ru.alexkorrnd.cloneapp.client.readingqrcode.di.qrCodeReaderModule

val modulesList = listOf(
    wifiDeviceInfoModule,
    wifiInteractorModule,
    wifiModule,
    converterModule,
    mainActivityModule,
    showQRCodeModule,
    qrCodeReaderModule
)