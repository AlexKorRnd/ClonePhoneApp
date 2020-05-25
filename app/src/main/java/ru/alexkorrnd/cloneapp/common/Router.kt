package ru.alexkorrnd.cloneapp.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class Router(
    private val fragmentManager: FragmentManager,
    private val containerResId: Int
) {


    fun replace(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .replace(containerResId, fragment)
            .commit()
    }
}