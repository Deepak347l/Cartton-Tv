package com.deepakdevloper.cartoontv

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.deepakdevloper.cartoontv.databinding.ActivityVideoPlayActicityBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener


class VideoPlayActicity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoPlayActicityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayActicityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }

        val videoId = intent.getStringExtra("VIDEO_ID").toString()

        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })

         binding.youtubePlayerView.addFullScreenListener(object:YouTubePlayerFullScreenListener{
             override fun onYouTubePlayerEnterFullScreen() {
                 // the video will continue playing in fullscreenView
                 this@VideoPlayActicity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
             }

             override fun onYouTubePlayerExitFullScreen() {
                 this@VideoPlayActicity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
             }

         })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}