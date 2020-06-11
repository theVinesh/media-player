package com.thevinesh.mediaplayer.util.permissionhelper

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class PermissionHelper(private val activity: AppCompatActivity) :
    IPermissionHelper {

    override fun isPermissionGranted(permission: String) =
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(activity.applicationContext, permission) -> true
            else -> false
        }

    override fun isPermissionGranted(requestCode: Int, grantResults: IntArray) =
        when (requestCode) {
            PERMISSION_REQ_CODE -> grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
            else -> false
        }

    override fun requestPermission(permission: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(
                arrayOf(permission),
                PERMISSION_REQ_CODE
            )
        }
    }

    companion object {
        const val PERMISSION_REQ_CODE = 42069
    }
}


