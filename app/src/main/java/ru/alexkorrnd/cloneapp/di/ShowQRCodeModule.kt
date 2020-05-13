package ru.alexkorrnd.cloneapp.di

import org.koin.dsl.module
import ru.alexkorrnd.cloneapp.generatingqrcode.ShowQrCodeFragment
import ru.alexkorrnd.cloneapp.generatingqrcode.ShowQrCodePresenter
import ru.alexkorrnd.cloneapp.generatingqrcode.data.QrCodeGenerator
import ru.alexkorrnd.cloneapp.generatingqrcode.data.QrCodeView

val showQRCodeModule = module {
    single { QrCodeGenerator() }
    scope<ShowQrCodeFragment> {
        scoped { (view: QrCodeView) ->
            ShowQrCodePresenter(
                wifiDeviceInfoLoader = get(),
                converter = get(),
                qrCodeGenerator = get(),
                view = view
            )
        }
    }
}