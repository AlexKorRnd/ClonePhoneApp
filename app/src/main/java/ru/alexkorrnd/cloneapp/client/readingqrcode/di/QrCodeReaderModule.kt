package ru.alexkorrnd.cloneapp.client.readingqrcode.di

import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.alexkorrnd.cloneapp.client.readingqrcode.CameraPreviewHelper
import ru.alexkorrnd.cloneapp.client.readingqrcode.QrCodeAnalyzer
import ru.alexkorrnd.cloneapp.client.readingqrcode.QrCodeImageAnalysisFabric
import java.util.concurrent.Executors

val qrCodeReaderModule = module {
    single<FirebaseVisionBarcodeDetector> {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
            .build()

        FirebaseVision.getInstance().getVisionBarcodeDetector(options)
    }

    single {
        QrCodeAnalyzer(
            detector = get()
        )
    }

    single(named(QR_CODE_READER_EXECUTOR_QUALIFIER)) { Executors.newSingleThreadExecutor() }

    single {
        QrCodeImageAnalysisFabric(
            executor = get(named(QR_CODE_READER_EXECUTOR_QUALIFIER)),
            qrCodeAnalyzer = get()
        )
    }

    single {
        CameraPreviewHelper(
            qrCodeImageAnalysisFabric = get()
        )
    }
}

private const val QR_CODE_READER_EXECUTOR_QUALIFIER = "QR_CODE_READER_EXECUTOR_QUALIFIER"