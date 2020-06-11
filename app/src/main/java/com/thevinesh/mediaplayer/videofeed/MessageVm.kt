package com.thevinesh.mediaplayer.videofeed

import androidx.lifecycle.ViewModel

class MessageVm(
    val message: String, val actionText: String? = null, val actionOnClick: () -> Unit = {}
) : ViewModel() {
    val showActionButton = actionText != null
}
