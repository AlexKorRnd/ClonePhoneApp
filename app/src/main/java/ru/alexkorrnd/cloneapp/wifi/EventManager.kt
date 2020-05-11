package ru.alexkorrnd.cloneapp.wifi

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable


class EventManager<T> {

   private val relay = PublishRelay.create<T>()

    val wifiEvents: Observable<T>
        get() = relay.hide()

    fun sendEvent(event: T) {
        relay.accept(event)
    }

}