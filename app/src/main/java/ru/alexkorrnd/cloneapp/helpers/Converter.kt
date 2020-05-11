package ru.alexkorrnd.cloneapp.helpers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converter(
    val gson: Gson
) {

    fun <Type> toString(data: Type): String {
        return gson.toJson(data)
    }

    inline fun <reified Type> fromString(string: String): Type {
        return gson.fromJson<Type>(string, object: TypeToken<Type>() {}.type)
    }
}