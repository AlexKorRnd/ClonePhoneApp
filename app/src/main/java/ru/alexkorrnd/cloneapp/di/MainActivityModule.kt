package ru.alexkorrnd.cloneapp.di

import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.alexkorrnd.cloneapp.wifi.WiFiDirectBroadcastReceiver

val mainActivityModule = module {

    single(named(WIFI_BROADCAST_RECEIVER_INTENT_FILTER_QUALIFIER)) {
        IntentFilter().apply {
            addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        }
    }

    single {
        WiFiDirectBroadcastReceiver(get())
    }
}

const val WIFI_BROADCAST_RECEIVER_INTENT_FILTER_QUALIFIER = "WIFI_BROADCAST_RECEIVER_INTENT_FILTER_QUALIFIER"