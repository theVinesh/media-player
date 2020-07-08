package com.thevinesh.mediaplayer.model

import android.net.Uri

data class Video(
    val id: Long,
    val uri: Uri,
    val width: Int,
    val height: Int,
    val name: String
)
