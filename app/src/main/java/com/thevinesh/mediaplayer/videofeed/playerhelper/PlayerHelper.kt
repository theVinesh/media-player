package com.thevinesh.mediaplayer.videofeed.playerhelper

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class PlayerHelper(private val context: Context) : IPlayerHelper {
    private var player: SimpleExoPlayer? = null
    private var playerView: PlayerView? = null

    private fun getMediaSource(title: String, contentUri: Uri): MediaSource {
        val factory = DefaultDataSourceFactory(context, title)
        return ExtractorMediaSource.Factory(factory)
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(contentUri)
    }

    private fun getExoPlayer(): SimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context)

    override fun play(playerView: PlayerView, title: String, contentUri: Uri) {
        this.playerView = playerView
        player = getExoPlayer()
        playerView.player = player
        player?.playWhenReady = true
        player?.prepare(getMediaSource(title, contentUri))
    }

    override fun release() {
        playerView?.player = null
        player?.release()
        player = null
    }
}
