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
import org.koin.android.ext.android.inject
import ru.alexkorrnd.cloneapp.MainActivity
import ru.alexkorrnd.cloneapp.R
import ru.alexkorrnd.cloneapp.client.connect.view.ConnectionFragment
import ru.alexkorrnd.cloneapp.client.readingqrcode.view.QrReaderCallback
import timber.log.Timber

class ReadingQRCodeFragment: Fragment(), QrReaderCallback {

    val cameraPreviewHelper: CameraPreviewHelper by inject()
    val qrCodeAnalyzer: QrCodeAnalyzer by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reading_qr_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isCameraPermissionGranted()) {
            bindCameraUseCases()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }
    }

    override fun onResume() {
        super.onResume()
        qrCodeAnalyzer.callback = this
    }

    override fun onPause() {
        qrCodeAnalyzer.callback = null
        super.onPause()
    }

    override fun onQrCodeFounded(barcode: FirebaseVisionBarcode) {
        Timber.i("barcode = ${barcode.rawValue}")
        (activity as MainActivity).router.replace(ConnectionFragment.newInstance(barcode.rawValue!!))
    }

    override fun onQrCodeNotFounded() {
    }

    override fun onQrCodeError(error: Exception) {
    }

    private fun isCameraPermissionGranted(): Boolean {
        val selfPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (isCameraPermissionGranted()) {
                bindCameraUseCases()
            } else {
                Toast.makeText(requireContext(), "Camera permission is required.", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
        }
    }

    private fun bindCameraUseCases() {
        viewFinder.post { cameraPreviewHelper.bindCameraUseCases(viewLifecycleOwner, viewFinder) }
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 10

        fun newInstance() = ReadingQRCodeFragment()
    }

}