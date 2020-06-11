package com.thevinesh.mediaplayer.util.resourceservice

import androidx.annotation.StringRes

interface IResourceService {
    fun getString(@StringRes resId: Int): String
}
