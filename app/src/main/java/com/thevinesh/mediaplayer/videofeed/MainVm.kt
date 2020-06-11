package com.thevinesh.mediaplayer.videofeed

import android.Manifest
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thevinesh.mediaplayer.BR
import com.thevinesh.mediaplayer.R
import com.thevinesh.mediaplayer.util.permissionhelper.IPermissionHelper
import com.thevinesh.mediaplayer.recyclerview.ViewProvider
import com.thevinesh.mediaplayer.recyclerview.viewBinder
import com.thevinesh.mediaplayer.recyclerview.viewProvider
import com.thevinesh.mediaplayer.repository.IMediaRepository
import com.thevinesh.mediaplayer.repository.MediaRepository.State.*
import com.thevinesh.mediaplayer.util.resourceservice.IResourceService
import com.thevinesh.mediaplayer.videofeed.playerhelper.IPlayerHelper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainVm(
    private val repository: IMediaRepository,
    private val permissionHelper: IPermissionHelper,
    private val playerHelper: IPlayerHelper,
    private val resourceService: IResourceService
) : ViewModel() {

    val data = ObservableArrayList<ViewModel>()
    val viewProvider = viewProvider {
        when (it) {
            is VideoVm -> R.layout.item_video
            is MessageVm -> R.layout.item_message
            else -> ViewProvider.NULL_VIEW
        }
    }
    val viewBinder = viewBinder { viewModel, binding ->
        binding.setVariable(BR.vm, viewModel)
    }

    private val requestPermissionMessageVm =
        MessageVm(
            resourceService.getString(R.string.req_permission_message),
            resourceService.getString(R.string.grant_and_proceed)
        ) { requestReadStoragePermission() }

    init {
        refresh()
    }

    fun releaseAllPlayers() {
        data.forEach {
            if (it is VideoVm) {
                it.release()
            }
        }
    }

    private fun refresh() {
        if (permissionHelper.isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            viewModelScope.launch {
                repository.getAllVideos().collect { state ->
                    when (state) {
                        is Loading -> clearAndAdd(
                            MessageVm(resourceService.getString(R.string.scanning))
                        )
                        is Success -> clearAndAddAll(
                            state.videos.map { VideoVm(it, playerHelper) }
                        )
                        is Message -> clearAndAdd(
                            MessageVm(state.message)
                        )
                    }
                }
            }
        } else {
            clearAndAdd(
                requestPermissionMessageVm
            )
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (permissionHelper.isPermissionGranted(requestCode, grantResults)) {
            refresh()
        } else {
            clearAndAdd(
                requestPermissionMessageVm
            )
        }
    }

    private fun requestReadStoragePermission() {
        permissionHelper.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun clearAndAddAll(vms: List<ViewModel>) {
        data.clear()
        data.addAll(vms)
    }

    private fun clearAndAdd(vm: ViewModel) {
        data.clear()
        data.add(vm)
    }

    override fun onCleared() {
        super.onCleared()
        releaseAllPlayers()
    }
}
