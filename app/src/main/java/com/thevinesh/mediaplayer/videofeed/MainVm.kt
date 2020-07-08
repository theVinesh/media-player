package com.thevinesh.mediaplayer.videofeed

import android.Manifest
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.thevinesh.mediaplayer.BR
import com.thevinesh.mediaplayer.R
import com.thevinesh.mediaplayer.recyclerview.ViewProvider
import com.thevinesh.mediaplayer.recyclerview.viewBinder
import com.thevinesh.mediaplayer.recyclerview.viewProvider
import com.thevinesh.mediaplayer.repository.IMediaRepository
import com.thevinesh.mediaplayer.repository.MediaRepository.State.*
import com.thevinesh.mediaplayer.util.displayservice.IDisplayService
import com.thevinesh.mediaplayer.util.permissionhelper.IPermissionHelper
import com.thevinesh.mediaplayer.util.resourceservice.IResourceService
import com.thevinesh.mediaplayer.videofeed.playerhelper.IPlayerHelper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainVm(
    private val repository: IMediaRepository,
    private val permissionHelper: IPermissionHelper,
    private val playerHelper: IPlayerHelper,
    private val resourceService: IResourceService,
    private val displayService: IDisplayService
) : ViewModel() {

    var currentItem = 0
    val currentVideo = ObservableField<VideoVm>()
    val videos = ObservableArrayList<ViewModel>()

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

    val onScrollListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPx: Int) {
           /* if (positionOffsetPx >= (displayService.getScreenHeight() / 2)){
                currentVideo.get()?.stop()
            }*/
        }

        override fun onPageSelected(position: Int) {
            val nextVideo = videos[position]
            if (nextVideo is VideoVm) {
                currentVideo.set(nextVideo)
                nextVideo.play()
            }
            currentItem = position
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
                            state.videos.map { VideoVm(it, playerHelper, displayService) }
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

    fun onResume() {
        currentVideo.get()?.play()
    }

    fun onPause() {
        playerHelper.stop()
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
        videos.clear()
        videos.addAll(vms)
    }

    private fun clearAndAdd(vm: ViewModel) {
        videos.clear()
        videos.add(vm)
    }

    override fun onCleared() {
        super.onCleared()
        playerHelper.release()
    }
}
