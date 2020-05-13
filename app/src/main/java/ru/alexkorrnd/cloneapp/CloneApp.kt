package ru.alexkorrnd.cloneapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.alexkorrnd.cloneapp.di.modulesList
import timber.log.Timber

class CloneApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@CloneApp)
            modules(modulesList)
        }
    }

}