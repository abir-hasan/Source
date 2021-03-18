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
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.util.MimeTypes
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
        private const val MEDIA_URL_DASH = R.string.media_url_dash
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
     *
     * v1 of initializePlayer method- Doesn't support Dash, this method is for Simple Streaming
     */
    private fun initializePlayerV1() {
        "initializePlayer() called".logDebug(TAG)
        player = SimpleExoPlayer.Builder(this).build()
        binding.exoPlayerView.player = player

        val mediaItem = MediaItem.fromUri(getString(VIDEO_URL))
        player?.setMediaItem(mediaItem)

        val secondMediaItem = MediaItem.fromUri(getString(AUDIO_URL))
        player?.addMediaItem(secondMediaItem) // Creating Playlist by adding a second item

        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
        player?.prepare()
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
     *
     * This Method supports DASH [Dynamic Adaptive Streaming over HTTP]
     */
    private fun initializePlayer() {
        "initializePlayer() called".logDebug(TAG)
        if (player == null) {
            val trackSelector = DefaultTrackSelector(this)
            // Is responsible for choosing tracks in the media item
            // To only pick tracks of standard definition or lower
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd())
            player = SimpleExoPlayer.Builder(this)
                .setTrackSelector(trackSelector) // Supporting Adaptive Streaming
                .build()
        }
        binding.exoPlayerView.player = player

        // MediaItem.Builder allows you to create MediaItems with a number of additional properties, including:
        //
        //1. The MIME type of the media content.
        //2. Protected content properties including the DRM type, license server URI and license request headers.
        //3. Side-loaded subtitle files to use during playback.
        //4. Clipping start and end positions.
        //5. An advert tag URI for advert insertion.
        //
        // MimeTypes.APPLICATION_MPD is for DASH
        // HLS (MimeTypes.APPLICATION_M3U8) and SmoothStreaming (MimeTypes.APPLICATION_SS) are other
        // commonly used adaptive streaming formats, both of which are supported by ExoPlayer.
        val mediaItem = MediaItem.Builder()
            .setUri(getString(MEDIA_URL_DASH))
            .setMimeType(MimeTypes.APPLICATION_MPD)
            .build()
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