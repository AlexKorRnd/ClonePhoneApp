package ru.alexkorrnd.cloneapp.client.readingqrcode

import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class CameraPreviewHelper(
    private val lifecycleOwner: LifecycleOwner,
    private val previewView: PreviewView,
    private val overlayView: ImageView,
    private val qrCodeCallback: QrCodeImageAnalysis.QrReaderCallback
) {

    private var camera: Camera? = null
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    private var imageAnalyzer: ImageAnalysis? = null

    private val mainExecutor: Executor = ContextCompat.getMainExecutor(previewView.context)

    fun bindCameraUseCases() {
        val metrics = DisplayMetrics().also { previewView.display.getRealMetrics(it) }
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)

        val rotation = previewView.display.rotation

        val cameraProviderFuture = ProcessCameraProvider.getInstance(previewView.context)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation)
                .build()

            preview.setSurfaceProvider(previewView.previewSurfaceProvider)

            imageAnalyzer = QrCodeImageAnalysis().apply {
                rectangleAreaHighliteHelper = RectangleAreaHighliteHelper(overlayView)
                callback = qrCodeCallback
            }.createQrCodeImageAnalysis(screenAspectRatio, rotation)

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build()

            cameraProvider.unbindAll()

            try {
                camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageAnalyzer
                )
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, mainExecutor)
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    companion object {

        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0

        private const val TAG = "CameraPreviewHelper"
    }

}