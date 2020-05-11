package ru.alexkorrnd.cloneapp.generatingqrcode.data

import android.graphics.Bitmap

interface QrCodeView {

    fun showBitmap(qrCode: Bitmap)

}