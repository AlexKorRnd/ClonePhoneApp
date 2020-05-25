package ru.alexkorrnd.cloneapp.common

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

abstract class BasePresenter {

    private val disposables = CompositeDisposable()

    fun detachView() {
        disposables.dispose()
    }

    fun Disposable.disposeOnFinish(): Disposable {
        disposables += this
        return this
    }
}