package ru.alexkorrnd.cloneapp.client.connect.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_connection.*
import org.koin.android.scope.lifecycleScope
import org.koin.core.parameter.parametersOf
import ru.alexkorrnd.cloneapp.R

class ConnectionFragment : Fragment(), ConnectionView {

    val presenter: ConnectionPresenter by lifecycleScope.inject { parametersOf(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deviceInfoAsString = requireArguments().getString(ARG_DEVICE_INFO)!!
        presenter.connect(deviceInfoAsString)
    }

    override fun showLoading() {
        loading.isVisible = true
    }

    override fun connected() {
        Toast.makeText(requireContext(), "Connected successfully!!!", Toast.LENGTH_LONG).show()
    }

    companion object {

        private const val ARG_DEVICE_INFO = "ARG_DEVICE_INFO"

        fun newInstance(deviceInfoAsString: String) = ConnectionFragment().apply {
            arguments = bundleOf(ARG_DEVICE_INFO to deviceInfoAsString)
        }
    }

}