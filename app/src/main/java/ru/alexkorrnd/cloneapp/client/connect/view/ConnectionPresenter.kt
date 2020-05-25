package ru.alexkorrnd.cloneapp.client.connect.view

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.alexkorrnd.cloneapp.client.connect.data.ConnectionInteractor
import ru.alexkorrnd.cloneapp.common.BasePresenter
import timber.log.Timber

class ConnectionPresenter(
    private val connectionInteractor: ConnectionInteractor,
    private val view: ConnectionView
): BasePresenter() {


    fun connect(deviceInfoAsString: String) {
        view.showLoading()
        connectionInteractor.connect(deviceInfoAsString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.connected()
            }, { error ->
                Timber.e(error)
            })
            .disposeOnFinish()
    }
}