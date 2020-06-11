package com.thevinesh.mediaplayer.recyclerview

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel

interface ViewProvider {
    companion object {
        const val NULL_VIEW = -1
    }

    @LayoutRes
    fun getView(vm: ViewModel): Int

}

fun viewProvider(provider: (vm: ViewModel) -> Int) = object :
    ViewProvider {
    override fun getView(vm: ViewModel) = provider.invoke(vm)
}
