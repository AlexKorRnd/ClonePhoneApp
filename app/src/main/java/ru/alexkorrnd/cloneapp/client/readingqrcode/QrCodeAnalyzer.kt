package ru.alexkorrnd.cloneapp.client.readingqrcode

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import ru.alexkorrnd.cloneapp.client.readingqrcode.view.QrReaderCallback


class QrCodeAnalyzer(
    private val detector: FirebaseVisionBarcodeDetector
): ImageAnalysis.Analyzer {

    var callback: QrReaderCallback? = null

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(image: ImageProxy) {
        val rotation = rotationDegreesToFirebaseRotation(image.imageInfo.rotationDegrees)
        val visionImage = FirebaseVisionImage.fromMediaImage(image.image!!, rotation)

        detector.detectInImage(visionImage)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    callback?.onQrCodeFounded(barcodes.first())
                    callback = null
                } else {
                    callback?.onQrCodeNotFounded()
                }

            }
            .addOnFailureListener {
                Log.e("QrCodeAnalyzer", "something went wrong", it)
                callback?.onQrCodeError(it)
            }
        image.close()
    }

    private fun rotationDegreesToFirebaseRotation(rotationDegrees: Int): Int {
        return when (rotationDegrees) {
            0 -> FirebaseVisionImageMetadata.ROTATION_0
            90 -> FirebaseVisionImageMetadata.ROTATION_90
            180 -> FirebaseVisionImageMetadata.ROTATION_180
            270 -> FirebaseVisionImageMetadata.ROTATION_270
            else -> throw IllegalArgumentException("Not supported")
        }
    }
}
