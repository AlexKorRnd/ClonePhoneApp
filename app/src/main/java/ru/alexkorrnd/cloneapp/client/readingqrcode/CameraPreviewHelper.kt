package ru.alexkorrnd.cloneapp.client.readingqrcode

import android.util.DisplayMetrics
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber
import java.util.concurrent.Executor
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class CameraPreviewHelper(
    private val qrCodeImageAnalysisFabric: QrCodeImageAnalysisFabric
) {

    private var camera: Camera? = null
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    fun bindCameraUseCases(
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView
    ) {
        val metrics = DisplayMetrics().also { previewView.display.getRealMetrics(it) }
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        val rotation = previewView.display.rotation

        val cameraProviderFuture = ProcessCameraProvider.getInstance(previewView.context)
        val mainExecutor: Executor = ContextCompat.getMainExecutor(previewView.context)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = setupPreview(screenAspectRatio, rotation)
            preview.setSurfaceProvider(previewView.previewSurfaceProvider)

            val imageAnalyzer = qrCodeImageAnalysisFabric.create(screenAspectRatio, rotation)

            val cameraSelector = setupCameraSelector()

            cameraProvider.unbindAll()

            try {
                camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageAnalyzer
                )
            } catch (exc: Exception) {
                Timber.e(exc, "Use case binding failed")
            }
        }, mainExecutor)
    }

    private fun setupPreview(
        screenAspectRatio: Int,
        rotation: Int
    ): Preview {
        return Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
    }

    private fun setupCameraSelector(): CameraSelector {
        return CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()
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