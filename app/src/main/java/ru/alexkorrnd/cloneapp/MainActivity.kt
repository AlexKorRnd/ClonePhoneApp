package ru.alexkorrnd.cloneapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import ru.alexkorrnd.cloneapp.generatingqrcode.ShowQrCodeFragment
import ru.alexkorrnd.cloneapp.wifi.WiFiDirectBroadcastReceiver
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val intentFilter = IntentFilter()

    private lateinit var channel: WifiP2pManager.Channel
    private lateinit var manager: WifiP2pManager

    private var receiver: WiFiDirectBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager.initialize(this.applicationContext, Looper.getMainLooper(), WifiP2pManager.ChannelListener {
            Timber.tag("WiFiDirectBroadcastReceiver")
            Timber.e("onChannelDisconnected!!!!!!!")
        })

        if (!LocationPermissionController.checkGrantedLocationPermission(this)) {
            LocationPermissionController.requestLocationPermission(this, REQUEST_PERMISSION)
        }

        showQrCode.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, ShowQrCodeFragment.newInstance(), null)
                .commit()
        }
    }

    /** register the BroadcastReceiver with the intent values to be matched  */
    public override fun onResume() {
        super.onResume()
        receiver = WiFiDirectBroadcastReceiver(
            this,
            manager,
            channel,
            getKoin().get()
        )
        registerReceiver(receiver, intentFilter)

    }

    public override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION) {
            val result = LocationPermissionController.processRequestPermissionsResult(this, permissions, grantResults)
            when(result) {
                PermissionController.PermissionResult.GRANTED -> {

                }
                PermissionController.PermissionResult.DENIED -> {

                }
                PermissionController.PermissionResult.NEVER_ASK_AGAIN -> {

                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val REQUEST_PERMISSION = 10
    }
}
