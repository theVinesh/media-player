package com.thevinesh.mediaplayer.bookmarkmanager

import android.content.SharedPreferences

class BookmarkManager(private val sharedPreferences: SharedPreferences) :
    IBookmarkManager {
    private val bookmarks = mutableListOf<Long>()

    init {
        refresh()
    }

    override fun isBookmarked(id: Long) = bookmarks.contains(id)

    override fun toggleBookmark(id: Long) {
        if (isBookmarked(id)) {
            bookmarks.remove(id)
        } else {
            bookmarks.add(id)
        }
        flushToPref()
    }

    fun refresh() {
        bookmarks.clear()
        sharedPreferences.getString(PREF_BOOKMARKS, null)?.let { csv ->
            csv.split(",").mapNotNull { it.toLongOrNull() }.let(bookmarks::addAll)
        }
    }

    fun flushToPref() {
        bookmarks.joinToString(",").let {
            sharedPreferences.edit().putString(PREF_BOOKMARKS, it).apply()
        }
    }

    companion object {
        const val PREF_BOOKMARKS = "com.thevinesh.mediaplayer.bookmarks"
    }
}
