package ru.alexkorrnd.cloneapp

import android.Manifest
import android.app.Activity
import android.content.Context

object LocationPermissionController {

    fun requestLocationPermission (activity: Activity, requestCode: Int) {
        PermissionController.requestPermissions(
            activity = activity,
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            permissionCode = requestCode
        )
    }


    fun checkGrantedLocationPermission(context: Context) =
        PermissionController.checkGrantedPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    /**
     * @return grantResultValue for requested permission
     * */
    fun processRequestPermissionsResult(activity: Activity, permissions: Array<out String>, grantResults: IntArray): PermissionController.PermissionResult {
        return PermissionController.processRequestPermissionsResult(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION,
            permissions,
            grantResults
        )
    }
}