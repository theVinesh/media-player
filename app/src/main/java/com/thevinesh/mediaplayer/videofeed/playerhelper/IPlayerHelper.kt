package com.thevinesh.mediaplayer.videofeed.playerhelper

import android.net.Uri

interface IPlayerHelper {
    fun play(title: String, contentUri: Uri)
    fun stop()
    fun release()
}
