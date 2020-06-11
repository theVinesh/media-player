package com.thevinesh.mediaplayer.videofeed.playerhelper

import android.net.Uri
import android.view.SurfaceView
import com.google.android.exoplayer2.ui.PlayerView

interface IPlayerHelper {
    fun play(playerView: PlayerView, title: String, contentUri: Uri)
    fun release()
}
