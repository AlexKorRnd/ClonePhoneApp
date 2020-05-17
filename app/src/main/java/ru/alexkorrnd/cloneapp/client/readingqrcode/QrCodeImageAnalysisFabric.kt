package ru.alexkorrnd.cloneapp.client.readingqrcode

import androidx.camera.core.ImageAnalysis
import java.util.concurrent.ExecutorService

class QrCodeImageAnalysisFabric(
    private val executor: ExecutorService,
    private val qrCodeAnalyzer: QrCodeAnalyzer
) {

    fun create(screenAspectRatio: Int, rotation: Int): ImageAnalysis {
        return ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
            .also { it.setAnalyzer(executor, qrCodeAnalyzer) }
    }
}