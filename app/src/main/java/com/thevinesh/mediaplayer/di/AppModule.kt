package com.thevinesh.mediaplayer.di

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import com.thevinesh.mediaplayer.BuildConfig
import com.thevinesh.mediaplayer.bookmarkmanager.BookmarkManager
import com.thevinesh.mediaplayer.repository.IMediaRepository
import com.thevinesh.mediaplayer.repository.MediaRepository
import com.thevinesh.mediaplayer.util.displayservice.DisplayService
import com.thevinesh.mediaplayer.util.displayservice.IDisplayService
import com.thevinesh.mediaplayer.util.permissionhelper.IPermissionHelper
import com.thevinesh.mediaplayer.util.permissionhelper.PermissionHelper
import com.thevinesh.mediaplayer.util.resourceservice.IResourceService
import com.thevinesh.mediaplayer.util.resourceservice.ResourceService
import com.thevinesh.mediaplayer.videofeed.MainVm
import com.thevinesh.mediaplayer.videofeed.playerhelper.IPlayerHelper
import com.thevinesh.mediaplayer.videofeed.playerhelper.PlayerHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    viewModel { (activity: AppCompatActivity, playerView: SurfaceView) ->
        MainVm(
            get(),
            get { parametersOf(activity) },
            get { parametersOf(playerView) },
            get(),
            get { parametersOf(activity) }
        )
    }
    single { androidApplication().getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE) }
    single { MediaRepository(androidContext()) } bind IMediaRepository::class
    single { (surfaceView: SurfaceView) ->
        PlayerHelper(surfaceView)
    } bind IPlayerHelper::class
    single { ResourceService(androidContext()) } bind IResourceService::class
    single { BookmarkManager(get()) }
    single { (activity: AppCompatActivity) ->
        PermissionHelper(activity)
    } bind IPermissionHelper::class
    factory { (activity: Activity) ->
        DisplayService(activity)
    } bind IDisplayService::class
}
