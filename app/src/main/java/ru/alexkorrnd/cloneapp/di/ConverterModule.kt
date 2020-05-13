package ru.alexkorrnd.cloneapp.di

import com.google.gson.Gson
import org.koin.dsl.module
import ru.alexkorrnd.cloneapp.helpers.Converter

val converterModule = module {
    single { Gson() }

    single {
        Converter(
            gson = get()
        )
    }
}