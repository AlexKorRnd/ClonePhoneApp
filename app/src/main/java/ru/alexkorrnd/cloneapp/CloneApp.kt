package ru.alexkorrnd.cloneapp

import android.app.Application
import ru.alexkorrnd.cloneapp.di.Injection
import timber.log.Timber

class CloneApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Injection.context = this
    }

}