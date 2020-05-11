package ru.alexkorrnd.cloneapp

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object PermissionController {

    fun checkGrantedPermission(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED


    fun requestPermissions(activity: Activity, permission: String, permissionCode: Int) {
        if (!checkGrantedPermission(activity, permission))
            ActivityCompat.requestPermissions(activity, listOf(permission).toTypedArray(), permissionCode)
    }


    fun shouldShowRequestPermissionRationale(activity: Activity, permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.shouldShowRequestPermissionRationale(permission)
        } else {
            true
        }
    }

    /**
    * @return grantResultValue for requested permission
    * */

    fun processRequestPermissionsResult(activity: Activity, requestedPermission: String,
                                        permissions: Array<out String>, grantResults: IntArray): PermissionResult {
        for (i in permissions.indices) {
            val permission = permissions[i]
            if (permission == requestedPermission) {
                val grantResult = grantResults[i]
                return if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    PermissionResult.GRANTED
                } else {
                    if (shouldShowRequestPermissionRationale(activity, permission)) {
                        PermissionResult.DENIED
                    } else {
                        PermissionResult.NEVER_ASK_AGAIN
                    }
                }

            }
        }
        return PermissionResult.DENIED
    }

    enum class PermissionResult {
        GRANTED,
        DENIED,
        NEVER_ASK_AGAIN
    }

}