package com.thevinesh.mediaplayer.videofeed

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ui.PlayerView
import com.thevinesh.mediaplayer.model.Video
import com.thevinesh.mediaplayer.bookmarkmanager.BookmarkManager
import com.thevinesh.mediaplayer.videofeed.playerhelper.IPlayerHelper
import org.koin.java.KoinJavaComponent.inject


class VideoVm(
    video: Video,
    private val helper: IPlayerHelper
) : ViewModel() {

    private val bookmarkManager: BookmarkManager by inject(
        BookmarkManager::class.java)

    val isBookmarked = ObservableBoolean(bookmarkManager.isBookmarked(video.id))
    val showThumb = ObservableBoolean(true)
    val title = video.name
    val contentUri = video.uri
    private val id = video.id

    fun toggleBookmark() {
        bookmarkManager.toggleBookmark(id)
        isBookmarked.set(bookmarkManager.isBookmarked(id))
    }

    fun play(playerView: PlayerView) {
        helper.play(playerView, title, contentUri)
        showThumb.set(false)
    }

    fun release() {
        showThumb.set(true)
        helper.release()
    }

    override fun onCleared() {
        super.onCleared()
        release()
    }
}
