package ru.alexkorrnd.cloneapp.generatingqrcode

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_show_qrcode.*
import ru.alexkorrnd.cloneapp.R
import ru.alexkorrnd.cloneapp.di.Injection
import ru.alexkorrnd.cloneapp.generatingqrcode.data.QrCodeView

class ShowQrCodeFragment: Fragment(), QrCodeView {

    private lateinit var presenter: ShowQrCodePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_qrcode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = Injection.provideShowQrCodePresenter(this)
        presenter.loadQrCode()
    }

    override fun showBitmap(qrCode: Bitmap) {
        qrCodeImage.setImageBitmap(qrCode)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ShowQrCodeFragment()
    }

}