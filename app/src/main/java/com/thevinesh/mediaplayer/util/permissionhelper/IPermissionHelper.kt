package com.thevinesh.mediaplayer.util.permissionhelper

interface IPermissionHelper {
    fun isPermissionGranted(permission: String): Boolean
    fun isPermissionGranted(requestCode: Int, grantResults: IntArray): Boolean
    fun requestPermission(permission: String)
}
