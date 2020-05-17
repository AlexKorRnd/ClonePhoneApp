package ru.alexkorrnd.cloneapp.client.readingqrcode

import android.util.Size
import androidx.camera.core.ImageAnalysis
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import timber.log.Timber
import java.util.concurrent.Executors

class QrCodeImageAnalysis {

    var rectangleAreaHighliteHelper: RectangleAreaHighliteHelper? = null
    var callback: QrCodeImageAnalysis.QrReaderCallback? = null

    fun createQrCodeImageAnalysis(screenAspectRatio: Int, rotation: Int): ImageAnalysis {
        val imageAnalyzer = ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
            .also {
                it.setAnalyzer(Executors.newSingleThreadExecutor(), QrCodeAnalyzer(object : QrCodeAnalyzer.Callback {
                    override fun success(barcode: FirebaseVisionBarcode, imageSize: Size) {
                        Timber.i("barcode = ${barcode.rawValue}")
                        rectangleAreaHighliteHelper?.highlightRectangle(imageSize, barcode.boundingBox!!)
                        callback?.onQrCodeFounded(barcode)
                    }

                    override fun notFound() {
                        rectangleAreaHighliteHelper?.clearRectangle()
                        callback?.onQrCodeNotFounded()
                    }

                    override fun error(it: java.lang.Exception) {
                        rectangleAreaHighliteHelper?.clearRectangle()
                        callback?.onQrCodeError(it)
                    }
                }))
            }
        return imageAnalyzer
    }

    interface QrReaderCallback {
        fun onQrCodeFounded(barcode: FirebaseVisionBarcode)

        fun onQrCodeNotFounded()

        fun onQrCodeError(error: Exception)
    }
}