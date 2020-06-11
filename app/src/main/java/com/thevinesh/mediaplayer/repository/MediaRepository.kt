package com.thevinesh.mediaplayer.repository

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.thevinesh.mediaplayer.R
import com.thevinesh.mediaplayer.model.Video
import com.thevinesh.mediaplayer.repository.MediaRepository.State.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class MediaRepository(private val context: Context) : IMediaRepository {

    override suspend fun getAllVideos() = flow {
        emit(Loading())
        val projection = arrayOf(
            MediaStore.Video.VideoColumns._ID,
            MediaStore.Video.VideoColumns.DISPLAY_NAME
        )
        val sortOrder = "${MediaStore.Video.VideoColumns.DATE_MODIFIED} DESC"

        val result = withContext(Dispatchers.IO) {
            context.applicationContext.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder
            )?.use { cursor ->
                try {
                    val videos = mutableListOf<Video>()

                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID)
                    val nameColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME)

                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(idColumn)
                        val uri =
                            ContentUris.withAppendedId(
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                id
                            )

                        val name = cursor.getString(nameColumn)
                        videos.add(Video(id, uri, name))
                    }
                    if (videos.isNotEmpty()) {

                        Success(videos)
                    } else {
                        Message(context.getString(R.string.media_empty))
                    }
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                    Message(e.message ?: context.getString(R.string.something_wrong))
                }
            }
        } ?: Message(context.getString(R.string.something_wrong))
        emit(result)
    }

    sealed class State {
        class Loading : State()
        class Success(val videos: List<Video>) : State()
        class Message(val message: String) : State()
    }
}

