package com.thevinesh.mediaplayer.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

interface ViewBinder {
    fun bind(vm: ViewModel, binding: ViewDataBinding)
}

fun viewBinder(binder: (viewModel: ViewModel, binding: ViewDataBinding) -> Unit) =
    object : ViewBinder {
        override fun bind(vm: ViewModel, binding: ViewDataBinding) = binder.invoke(vm, binding)
    }
