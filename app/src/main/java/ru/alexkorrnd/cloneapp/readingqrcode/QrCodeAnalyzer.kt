package ru.alexkorrnd.cloneapp.readingqrcode

import android.annotation.SuppressLint
import android.util.Log
import android.util.Size
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata


class QrCodeAnalyzer(
    private val callback: Callback
) : ImageAnalysis.Analyzer {

    private lateinit var imageSize: Size

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(image: ImageProxy) {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
            .build()

        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)

        val rotation = rotationDegreesToFirebaseRotation(image.imageInfo.rotationDegrees)
        val visionImage = FirebaseVisionImage.fromMediaImage(image.image!!, rotation)
        Log.d(TAG, "visionImage = ${visionImage.bitmap.height}, ${visionImage.bitmap.width}")


        imageSize = Size(visionImage.bitmap.width, visionImage.bitmap.height)

        detector.detectInImage(visionImage)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    callback.success(barcodes.first(), imageSize)
                } else {
                    callback.notFound()
                }

            }
            .addOnFailureListener {
                Log.e("QrCodeAnalyzer", "something went wrong", it)
                callback.error(it)
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

    interface Callback {
        fun success(barcode: FirebaseVisionBarcode, imageSize: Size)

        fun notFound()

        fun error(it: Exception)
    }

    companion object {

        const val TAG = "QrCodeAnalyzer"
    }
}
