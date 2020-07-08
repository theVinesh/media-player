package com.thevinesh.mediaplayer.util

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.thevinesh.mediaplayer.recyclerview.DataBindingRecyclerViewAdapter
import com.thevinesh.mediaplayer.recyclerview.ViewBinder
import com.thevinesh.mediaplayer.recyclerview.ViewProvider

@BindingAdapter("viewProvider", "viewBinder", "data")
fun setAdapter(
    view: ViewPager2, viewProvider: ViewProvider, viewBinder: ViewBinder, items: List<ViewModel>
) {
    view.adapter = DataBindingRecyclerViewAdapter(viewProvider, viewBinder).apply {
        data = items
    }
}

@BindingAdapter("thumb")
fun loadImage(view: ImageView, uri: Uri) {
    Glide.with(view)
        .load(uri)
        .fitCenter()
        .into(view)
}

@BindingAdapter("visibleOrGone")
fun setVisibleOrGone(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("width", "height")
fun setDimen(view: View, width: Int, height: Int) {
    val layoutParams = view.layoutParams.apply {
        this.width = width
        this.height = height
    }
    view.layoutParams = layoutParams
    view.requestLayout()
}

@BindingAdapter("offsetY")
fun translateYByOffset(view: View, translateOffset: Float) {
    view.animate().translationY(translateOffset)
}
