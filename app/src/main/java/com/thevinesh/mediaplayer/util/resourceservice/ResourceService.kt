package com.thevinesh.mediaplayer.util.resourceservice

import android.content.Context

class ResourceService(private val context: Context) : IResourceService {
    override fun getString(resId: Int): String = context.getString(resId)
}
