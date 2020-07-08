package com.thevinesh.mediaplayer.videofeed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.thevinesh.mediaplayer.R
import com.thevinesh.mediaplayer.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainVm by inject { parametersOf(this, binding.playerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        with(binding) {
            vm = viewModel
            videoList.registerOnPageChangeCallback(viewModel.onScrollListener)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        viewModel.onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}
