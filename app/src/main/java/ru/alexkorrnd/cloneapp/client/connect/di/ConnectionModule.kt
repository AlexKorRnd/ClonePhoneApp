package ru.alexkorrnd.cloneapp.client.connect.di

import org.koin.dsl.module
import ru.alexkorrnd.cloneapp.client.connect.data.ConnectToDevice
import ru.alexkorrnd.cloneapp.client.connect.data.ConnectionInteractor
import ru.alexkorrnd.cloneapp.client.connect.data.WiFiConfigCreator
import ru.alexkorrnd.cloneapp.client.connect.view.ConnectionFragment
import ru.alexkorrnd.cloneapp.client.connect.view.ConnectionPresenter
import ru.alexkorrnd.cloneapp.client.connect.view.ConnectionView
import ru.alexkorrnd.cloneapp.client.find.FindPeerByDeviceInfo

val connectionModule = module {

    single {
        FindPeerByDeviceInfo(
            manager = get()
        )
    }

    single {
        WiFiConfigCreator()
    }

    single {
        ConnectToDevice(
            manager = get(),
            wiFiConfigCreator = get()
        )
    }

    single {
        ConnectionInteractor(
            findPeerByDeviceInfo = get(),
            connectToDevice = get(),
            eventManager = get(),
            converter = get()
        )
    }

    scope<ConnectionFragment> {
        scoped{ (view: ConnectionView) ->
            ConnectionPresenter(
                connectionInteractor = get(),
                view = view
            )
        }
    }
}