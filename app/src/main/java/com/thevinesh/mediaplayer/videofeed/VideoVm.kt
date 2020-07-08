package com.thevinesh.mediaplayer.videofeed

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.thevinesh.mediaplayer.bookmarkmanager.BookmarkManager
import com.thevinesh.mediaplayer.model.Video
import com.thevinesh.mediaplayer.util.displayservice.IDisplayService
import com.thevinesh.mediaplayer.videofeed.playerhelper.IPlayerHelper
import org.koin.java.KoinJavaComponent.inject


class VideoVm(
    video: Video, private val helper: IPlayerHelper, displayService: IDisplayService
) : ViewModel() {

    private val bookmarkManager: BookmarkManager by inject(
        BookmarkManager::class.java
    )

    val isBookmarked = ObservableBoolean(bookmarkManager.isBookmarked(video.id))
    val showThumb = ObservableBoolean(true)
    val title = video.name
    val contentUri = video.uri
    val height: Int
    val width: Int

    private val id = video.id

    init {
        val screenWidth = displayService.getScreenWidth()
        val screenHeight = displayService.getScreenHeight()

        if (video.height > video.width) {
            //portrait
            width = video.width * screenHeight / video.height
            height = screenHeight
        } else {
            //landscape
            width = screenWidth
            height = video.height * screenWidth / video.width
        }
    }


    fun toggleBookmark() {
        bookmarkManager.toggleBookmark(id)
        isBookmarked.set(bookmarkManager.isBookmarked(id))
    }

    fun play() {
        helper.play(title, contentUri)
        showThumb.set(false)
    }

    fun stop() {
        helper.stop()
        showThumb.set(true)
    }

    override fun onCleared() {
        super.onCleared()
        stop()
    }
}
