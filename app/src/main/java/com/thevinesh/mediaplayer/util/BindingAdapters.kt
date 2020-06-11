package com.thevinesh.mediaplayer.util

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thevinesh.mediaplayer.recyclerview.DataBindingRecyclerViewAdapter
import com.thevinesh.mediaplayer.recyclerview.ViewBinder
import com.thevinesh.mediaplayer.recyclerview.ViewProvider

@BindingAdapter("viewProvider", "viewBinder", "data")
fun setAdapter(
    view: RecyclerView, viewProvider: ViewProvider, viewBinder: ViewBinder, items: List<ViewModel>
) {
    view.adapter = DataBindingRecyclerViewAdapter(viewProvider, viewBinder).apply {
        data = items
    }
}

@BindingAdapter("snap")
fun setSnap(view: RecyclerView, snap: Boolean) {
    if (snap) {
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(view)
    }
}

@BindingAdapter("thumb")
fun setSnap(view: ImageView, uri: Uri) {
    Glide.with(view)
        .load(uri)
        .fitCenter()
        .into(view)
}

@BindingAdapter("visibleOrGone")
fun setVisibleOrGone(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}
