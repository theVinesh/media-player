package com.thevinesh.mediaplayer.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.thevinesh.mediaplayer.recyclerview.DataBindingRecyclerViewAdapter.GenericViewHolder

class DataBindingRecyclerViewAdapter(
    private val viewProvider: ViewProvider, private val viewBinder: ViewBinder
) : RecyclerView.Adapter<GenericViewHolder>() {

    var data = emptyList<ViewModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GenericViewHolder.create(parent, viewType)

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) =
        holder.itemViewBinding.run {
            val vm = data[position]
            viewBinder.bind(vm, this)
            executePendingBindings()
        }

    override fun getItemViewType(position: Int) = viewProvider.getView(data[position])

    class GenericViewHolder(val itemViewBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        companion object {
            fun create(parent: ViewGroup, @LayoutRes layoutId: Int): GenericViewHolder {
                val binding: ViewDataBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), layoutId, parent, false
                )
                return GenericViewHolder(binding)
            }
        }
    }
}
