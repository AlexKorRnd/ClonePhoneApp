package ru.alexkorrnd.cloneapp

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import ru.alexkorrnd.cloneapp.client.readingqrcode.ReadingQRCodeFragment
import ru.alexkorrnd.cloneapp.generatingqrcode.ShowQrCodeFragment
import ru.alexkorrnd.cloneapp.wifi.WiFiDirectBroadcastReceiver

class MainActivity : AppCompatActivity() {

    val intentFilter : IntentFilter by inject()

    val receiver: WiFiDirectBroadcastReceiver by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!LocationPermissionController.checkGrantedLocationPermission(this)) {
            LocationPermissionController.requestLocationPermission(this, REQUEST_PERMISSION)
        }

        showQrCode.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, ShowQrCodeFragment.newInstance(), null)
                .commit()
        }
        readQrCode.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, ReadingQRCodeFragment.newInstance(), null)
                .commit()
        }
    }

    public override fun onResume() {
        super.onResume()
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
