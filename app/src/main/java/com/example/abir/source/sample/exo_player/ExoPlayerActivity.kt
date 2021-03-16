package com.example.abir.source.sample.exo_player

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.abir.source.R
import com.example.abir.source.databinding.ActivityExoPlayerBinding
import com.example.abir.source.utils.logDebug
import com.example.abir.source.utils.logError
import com.example.abir.source.utils.logWarn
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util

/**
 * Links to follow
 * Code Lab - https://developer.android.com/codelabs/exoplayer-intro#0
 * Documentation - https://exoplayer.dev/hello-world.html
 * Github - https://github.com/google/ExoPlayer
 */
class ExoPlayerActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ExoPlayerActivity"
        private const val VIDEO_URL = R.string.media_url_mp4
        private const val AUDIO_URL = R.string.media_url_mp3
        private const val AUDIO_URL_2 = R.string.media_url_mp3_v2
    }

    private lateinit var binding: ActivityExoPlayerBinding

    private var player: SimpleExoPlayer? = null

    private var playWhenReady: Boolean = true // Play-Pause State
    private var currentWindow: Int = 0 // Current window index
    private var playbackPosition: Long = 0 // Current Playback position

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    /**
     * hideSystemUi is a helper method called in onResume,
     * which allows you to have a full-screen experience.
     */
    private fun hideSystemUi() {
        binding.exoPlayerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }


    /**
     * Android API level 24 and higher supports multiple windows.
     * As your app can be visible, but not active in split window mode,
     * you need to initialize the player in onStart.
     * Android API level 24 and lower requires you to wait as long as possible
     * until you grab resources, so you wait until onResume before initializing the player.
     *
     * Here's what's happening:
     *
     * setPlayWhenReady - tells the player whether to start playing as soon as all resources for
     * playback have been acquired. Because playWhenReady is initially true,
     * playback starts automatically the first time the app is run.
     *
     * seekTo -  tells the player to seek to a certain position within a specific window.
     * Both currentWindow and playbackPosition are initialized to zero so that playback starts
     * from the very start the first time the app is run.
     *
     * prepare -  tells the player to acquire all the resources required for playback.
     */
    private fun initializePlayer() {
        "initializePlayer() called".logDebug(TAG)
        player = SimpleExoPlayer.Builder(this).build()
        binding.exoPlayerView.player = player

        val mediaItem = MediaItem.fromUri(getString(VIDEO_URL))
        player?.setMediaItem(mediaItem)

        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
        player?.prepare()
    }

    /**
     * With API Level 24 and lower, there is no guarantee of onStop being called,
     * so you have to release the player as early as possible in onPause.
     * With API Level 24 and higher (which brought multi- and split-window mode),
     * onStop is guaranteed to be called. In the paused state, your activity is still visible,
     * so you wait to release the player until onStop.
     */
    private fun releasePlayer() {
        player?.let {
            playWhenReady = it.playWhenReady
            playbackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            it.release()
            player = null
            "releasePlayer() resources released".logWarn(TAG)
        } ?: run {
            "releasePlayer() player null".logError(TAG)
        }
    }
}