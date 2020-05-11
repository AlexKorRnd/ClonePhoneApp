package ru.alexkorrnd.cloneapp.generatingqrcode.data

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

class QrCodeGenerator {

    fun generate(data: String): Bitmap {
        val multiFormatWriter = MultiFormatWriter();
        val bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 340, 340);
        val barcodeEncoder = BarcodeEncoder();
        return barcodeEncoder.createBitmap(bitMatrix);
    }

}