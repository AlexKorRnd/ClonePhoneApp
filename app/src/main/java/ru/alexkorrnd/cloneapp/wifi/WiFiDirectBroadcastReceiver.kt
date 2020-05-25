package ru.alexkorrnd.cloneapp.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import timber.log.Timber

class WiFiDirectBroadcastReceiver(
    private val wifiInteractor: WifiInteractor
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("onReceive:: action = ${intent.action}")
        when(intent.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {

            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                wifiInteractor.processEvent(WiFiEvent.PeersChanged())
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {

            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                val deviceInfo = intent.getParcelableExtra<WifiP2pDevice>(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE) ?: return
                wifiInteractor.processEvent(WiFiEvent.DeviceInfoChanged(deviceInfo))
            }
        }
    }

    /*fun connect() {
        // Picking the first device found on the network.
        val device = peers[0]

        val config = WifiP2pConfig().apply {
            deviceAddress = device.deviceAddress
            wps.setup = WpsInfo.PBC
        }


        manager.connect(channel, config, object : WifiP2pManager.ActionListener {

            override fun onSuccess() {
                // WiFiDirectBroadcastReceiver notifies us. Ignore for now.
                Toast.makeText(
                    context,
                    "Connected!!!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(reason: Int) {
                Toast.makeText(
                    context,
                    "Connect failed. Reason $reason. Retry.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }*/

}