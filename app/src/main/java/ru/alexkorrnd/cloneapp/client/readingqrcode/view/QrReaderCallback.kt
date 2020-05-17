package ru.alexkorrnd.cloneapp.client.readingqrcode.view

import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode

interface QrReaderCallback {
    fun onQrCodeFounded(barcode: FirebaseVisionBarcode)

    fun onQrCodeNotFounded()

    fun onQrCodeError(error: Exception)
}
