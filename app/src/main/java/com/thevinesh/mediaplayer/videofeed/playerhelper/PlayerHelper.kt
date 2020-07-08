package com.thevinesh.mediaplayer.videofeed.playerhelper

import android.net.Uri
import android.view.SurfaceView
import com.google.android.exoplayer2.C.VIDEO_SCALING_MODE_SCALE_TO_FIT
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class PlayerHelper(private val playerSurfaceView: SurfaceView) : IPlayerHelper {

    private var player: SimpleExoPlayer? = null
    private val context = playerSurfaceView.context

    private fun getMediaSource(title: String, contentUri: Uri): MediaSource {
        val factory = DefaultDataSourceFactory(context, title)
        return ExtractorMediaSource.Factory(factory)
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(contentUri)
    }

    private fun getExoPlayer(): SimpleExoPlayer =
        ExoPlayerFactory.newSimpleInstance(context).apply {
            videoScalingMode = VIDEO_SCALING_MODE_SCALE_TO_FIT
            setVideoSurfaceView(playerSurfaceView)
        }

    override fun play(title: String, contentUri: Uri) {
        if (player == null) {
            player = getExoPlayer()
        }
        player?.playWhenReady = true
        player?.prepare(getMediaSource(title, contentUri))
    }

    override fun stop() {
        player?.stop()
    }

    override fun release() {
        player?.setVideoSurfaceView(null)
        player?.release()
        player = null
    }
}
