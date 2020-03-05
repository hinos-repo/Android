package com.mysample.exoIcyProject.lib

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Message
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory

import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import com.mysample.exoIcyProject.component.PlayStateReceiver
import okhttp3.OkHttpClient
import saschpe.exoplayer2.ext.icy.IcyHttpDataSourceFactory
import java.lang.ref.WeakReference

class XwIcyExoPlayer(internal var m_context: Context) : XwBasePlayer(XwBasePlayer.PLAYER_EXOPLAYER){

    var m_exoPlayer: SimpleExoPlayer? = null
    val m_hSendTitle = WeakHandler()

    val bandwidthMeter: DefaultBandwidthMeter by lazy { DefaultBandwidthMeter() }
    val trackSelectionFactory: AdaptiveTrackSelection.Factory by lazy { AdaptiveTrackSelection.Factory(bandwidthMeter) }
    val trackSelector: DefaultTrackSelector by lazy { DefaultTrackSelector(trackSelectionFactory) }

    var eventListener: Player.EventListener = object : Player.EventListener
    {
        override fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {
            Log.d(TAG, "onTimelineChanged: ")
        }

        override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray)
        {
            Log.d(TAG, "onTimelineChanged: ")
        }

        override fun onLoadingChanged(isLoading: Boolean)
        {
            Log.d(TAG, "onTimelineChanged: ")
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            if (playWhenReady) {
                if (playbackState == ExoPlayer.STATE_READY) {
                    m_hPlayResultListener?.onPlayResult(true)
                    state = XwBasePlayer.STATE_PLAY
                }
            }
        }

        override fun onRepeatModeChanged(repeatMode: Int)
        {
            Log.d(TAG, "onRepeatModeChanged: ")
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean)
        {
            Log.d(TAG, "onShuffleModeEnabledChanged: ")
        }

        override fun onPlayerError(error: ExoPlaybackException)
        {
            Log.d(TAG, "onPlayerError: " + error.message)
            if (state == XwBasePlayer.STATE_READY) {
                m_hPlayResultListener?.onPlayResult(false)
                stop()
            }
        }
        override fun onPositionDiscontinuity(reason: Int) {}
        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
        override fun onSeekProcessed() {}
    }

    private val userAgent: String
        get() = Util.getUserAgent(m_context, javaClass.simpleName)

    override fun play(context: Context, strUrl : String): Boolean
    {
        var nNewState = m_nState

        try {
            if (m_exoPlayer == null)
            {
                m_exoPlayer = ExoPlayerFactory.newSimpleInstance(m_context, trackSelector)
            } else {
                if (m_exoPlayer!!.playWhenReady) {
                    m_exoPlayer!!.stop()
                    nNewState = XwBasePlayer.STATE_STOP
                }
                m_exoPlayer!!.seekTo(0)
                m_exoPlayer!!.playWhenReady = false
            }

            val mediaSource = buildMediaSource(strUrl)
            m_exoPlayer!!.prepare(mediaSource)
            m_exoPlayer!!.playWhenReady = true
            m_exoPlayer!!.addListener(eventListener)

            state = XwBasePlayer.STATE_READY

        } catch (e: Exception) {
            Log.e(TAG, "play exception1 :" + e.message)
            try {
                if (m_exoPlayer != null) {
                    m_exoPlayer!!.stop()
                    m_exoPlayer!!.stop(true)
                    m_exoPlayer!!.release()
                    m_exoPlayer = null
                    state = XwBasePlayer.STATE_STOP
                }
            } catch (e2: Exception) {
                Log.e(TAG, "play exception2 : " + e2.message)
            }

            return false
        }

        return true
    }

    private fun buildMediaSource(strUrl : String): MediaSource
    {
        val uri = Uri.parse(strUrl)
        val userAgent = Util.getUserAgent(m_context, "ExoPlayer-code")
        if (uri.lastPathSegment != null) {
            Log.d(TAG, "buildMediaSource: " + uri.lastPathSegment!!)
        }

        val client = OkHttpClient.Builder().build()
        val icyHttpDataSourceFactory = IcyHttpDataSourceFactory.Builder(client)
                .setUserAgent(userAgent)
                .setIcyMetadataChangeListener {
                    val strTitle = it.getStreamTitle()
                    if (!strTitle.isNullOrEmpty())
                    {
                        val long = m_exoPlayer!!.totalBufferedDuration
                        val msg = Message()

                        msg.obj = strTitle
                        if (long >= 1000)
                        {
                            m_hSendTitle.sendMessageDelayed(msg, long - 1000)
                        }
                        else
                        {
                            m_hSendTitle.sendMessageDelayed(msg, long)
                        }
                    }
                }.build()


        return if (strUrl.contains("rtmp:"))
        {
            ExtractorMediaSource.Factory(RtmpDataSourceFactory(null)).createMediaSource(uri)
//            ExtractorMediaSource.Factory(RtmpDataSourceFactory(transferListener)).createMediaSource(uri)
        }
        else if(strUrl.contains("mms"))
        {
            HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri)
        }
        else
        {
//            ExtractorMediaSource.Factory(DefaultDataSourceFactory(m_context, userAgent)).setExtractorsFactory(DefaultExtractorsFactory()).createMediaSource(uri)
            ExtractorMediaSource.Factory(DefaultDataSourceFactory(m_context, null, icyHttpDataSourceFactory)).setExtractorsFactory(DefaultExtractorsFactory()).createMediaSource(uri)
//            ExtractorMediaSource.Factory(DefaultDataSourceFactory(m_context, transferListener, icyHttpDataSourceFactory)).setExtractorsFactory(DefaultExtractorsFactory()).createMediaSource(uri)
        }
    }

    inner class WeakHandler : Handler()
    {
        var weakExo : WeakReference<XwIcyExoPlayer> = WeakReference(this@XwIcyExoPlayer)

        override fun handleMessage(msg: Message?)
        {
            val weak = weakExo.get()
            weak?.let {
                val strTitle = msg?.obj.toString()
                PlayStateReceiver.sendTitle(m_context, strTitle)
            }
        }
    }

    val transferListener : TransferListener = object : TransferListener
    {
        override fun onTransferInitializing(source: DataSource?, dataSpec: DataSpec?, isNetwork: Boolean) {

        }
        override fun onTransferStart(source: DataSource?, dataSpec: DataSpec?, isNetwork: Boolean) {

        }

        override fun onTransferEnd(source: DataSource?, dataSpec: DataSpec?, isNetwork: Boolean) {

        }

        override fun onBytesTransferred(source: DataSource?, dataSpec: DataSpec?, isNetwork: Boolean, bytesTransferred: Int) {

        }
    }

    override fun stop()
    {
        try {
            if (m_exoPlayer != null) {
                m_exoPlayer!!.stop()
                m_exoPlayer!!.stop(true)
            }
        } catch (e: Exception) {
            Log.e(TAG, "stop exception :" + e.message)
        }

        state = XwBasePlayer.STATE_STOP
    }

    override fun release()
    {
        try {
            if (m_exoPlayer != null) {
                m_exoPlayer!!.release()
                m_exoPlayer = null
            }
        } catch (e: Exception) {
            Log.e(TAG, "release exception :" + e.message)
        }

        state = XwBasePlayer.STATE_STOP
    }

    override fun setVolume(left: Float, right: Float)
    {
        if (m_exoPlayer != null) {
            try {
                m_exoPlayer!!.volume = left
            } catch (e: Exception) {
                Log.e(TAG, "setVolume: " + e.message)
            }
        }
    }

    companion object
    {
        private val TAG = XwMediaPlayer::class.java.simpleName
    }
}