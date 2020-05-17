package ru.alexkorrnd.cloneapp.client.readingqrcode

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import kotlinx.android.synthetic.main.fragment_reading_qr_code.*
import ru.alexkorrnd.cloneapp.R
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ReadingQRCodeFragment: Fragment() {

    private lateinit var cameraPreviewHelper: CameraPreviewHelper
    private lateinit var qrCodeImageAnalysis: QrCodeImageAnalysis
    private lateinit var mainCameraExecutor: Executor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reading_qr_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainCameraExecutor = Executors.newSingleThreadExecutor()

        cameraPreviewHelper = CameraPreviewHelper(
            lifecycleOwner = this,
            previewView = viewFinder,
            overlayView = imageView,
            qrCodeCallback = object : QrCodeImageAnalysis.QrReaderCallback {
                override fun onQrCodeFounded(barcode: FirebaseVisionBarcode) {

                }

                override fun onQrCodeNotFounded() {

                }

                override fun onQrCodeError(error: Exception) {

                }
            }
        )
        qrCodeImageAnalysis = QrCodeImageAnalysis()
        qrCodeImageAnalysis.rectangleAreaHighliteHelper = RectangleAreaHighliteHelper(imageView)

        if (isCameraPermissionGranted()) {
            viewFinder.post { cameraPreviewHelper.bindCameraUseCases() }
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }

        viewFinder.post {
            cameraPreviewHelper.bindCameraUseCases()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Request camera permissions

    }

    private fun isCameraPermissionGranted(): Boolean {
        val selfPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (isCameraPermissionGranted()) {
                viewFinder.post { cameraPreviewHelper.bindCameraUseCases() }
            } else {
                Toast.makeText(requireContext(), "Camera permission is required.", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
        }
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 10

        fun newInstance() = ReadingQRCodeFragment()
    }

}