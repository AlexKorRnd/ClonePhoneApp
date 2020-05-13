package ru.alexkorrnd.cloneapp.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast
import timber.log.Timber

class WiFiDirectBroadcastReceiver(
    private val context: Context,
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val wifiInteractor: WifiInteractor
) : BroadcastReceiver() {

    private val peers = mutableListOf<WifiP2pDevice>()

    private val peerListListener = WifiP2pManager.PeerListListener { peerList ->
        val refreshedPeers = peerList.deviceList
        Timber.v("founded ${refreshedPeers.size} peers")
        refreshedPeers.forEach {
            Timber.i("peer = ${it.toString()}")
        }
        if (refreshedPeers != peers) {
            peers.clear()
            peers.addAll(refreshedPeers)

            // If an AdapterView is backed by this data, notify it
            // of the change. For instance, if you have a ListView of
            // available peers, trigger an update.
            //(listAdapter as WiFiPeerListAdapter).notifyDataSetChanged()

            // Perform any other updates needed based on the new list of
            // peers connected to the Wi-Fi P2P network.
        }

        if (peers.isEmpty()) {
            Timber.d("No devices found")
            return@PeerListListener
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("onReceive:: action = ${intent.action}")
        when(intent.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                // Determine if Wifi P2P mode is enabled or not, alert
                // the Activity.
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {

            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {

                // Connection state changed! We should probably do something about
                // that.
                val networkInfo: NetworkInfo? = intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO) as NetworkInfo

                if (networkInfo?.isConnected == true) {

                    // We are connected with the other device, request connection
                    // info to find group owner IP

                    //manager.requestConnectionInfo(channel, connectionListener)
                }
            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                val deviceInfo = intent.getParcelableExtra<WifiP2pDevice>(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE) ?: return
                wifiInteractor.processEvent(WiFiEvent.DeviceInfoChanged(deviceInfo))
            }
        }
    }

    fun discoverPeers() {
        manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {

            override fun onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank. Code for peer discovery goes in the
                // onReceive method, detailed below.
                Timber.d("discoverPeers:: onSuccess")
                manager.requestPeers(channel, peerListListener)
            }

            override fun onFailure(reasonCode: Int) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
                Timber.d("discoverPeers:: onFailure: reasonCode = $reasonCode")
            }
        })
    }

    fun connect() {
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
    }

}