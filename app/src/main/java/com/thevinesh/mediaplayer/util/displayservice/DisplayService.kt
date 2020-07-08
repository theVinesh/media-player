package com.thevinesh.mediaplayer.util.displayservice

import android.app.Activity
import android.util.DisplayMetrics

class DisplayService(activity: Activity) : IDisplayService {
    private val displayMetrics = DisplayMetrics()

    init {
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
    }

    override fun getScreenWidth() = displayMetrics.widthPixels

    override fun getScreenHeight() = displayMetrics.heightPixels
}
