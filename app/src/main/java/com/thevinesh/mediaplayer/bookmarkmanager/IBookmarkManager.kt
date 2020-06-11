package com.thevinesh.mediaplayer.bookmarkmanager

interface IBookmarkManager {
    fun isBookmarked(id: Long): Boolean
    fun toggleBookmark(id: Long)
}
