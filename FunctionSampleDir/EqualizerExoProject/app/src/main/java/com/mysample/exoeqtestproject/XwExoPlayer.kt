package com.mysample.exoeqtestproject

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import okhttp3.OkHttpClient
import saschpe.exoplayer2.ext.icy.IcyHttpDataSource
import saschpe.exoplayer2.ext.icy.IcyHttpDataSourceFactory

class XwExoPlayer(val m_context : Context)
{
    private var m_exoPlayer : SimpleExoPlayer? = null
    private var m_strTitle : String = ""
    private var m_nSessionId = -1

    fun initPlayer() : Boolean
    {
        if(m_exoPlayer != null)
            return false

        val bandwidthMeter = DefaultBandwidthMeter()
        val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(trackSelectionFactory)
        m_exoPlayer = ExoPlayerFactory.newSimpleInstance(m_context, trackSelector)
        return true
    }

    private fun buildMediaSource(strUrl : String): MediaSource
    {
        val uri = Uri.parse(strUrl)

        val userAgent = Util.getUserAgent(m_context, "ExoPlayer-code")
        val icyFactory = bringToTitleListener(userAgent)

        return if (strUrl.contains("rtmp:"))
        {
//            ExtractorMediaSource.Factory(DefaultDataSourceFactory(m_context, null, icyHttpDataSourceFactory)).createMediaSource(uri)
            ExtractorMediaSource.Factory(RtmpDataSourceFactory(null)).createMediaSource(uri)
        }
        else if(strUrl.contains("mms")) //안됨
        {
            HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri)
        }
        else
        {
            ExtractorMediaSource.Factory(DefaultDataSourceFactory(m_context, null, icyFactory)).createMediaSource(uri)
        }
    }

    fun play(strUrl : String) : Boolean
    {
        if(strUrl.isNullOrEmpty())
        {
            return false
        }

        val nResult = initPlayer()
        if(!nResult)
        {
           return false
        }

        val mediaSource = buildMediaSource(strUrl)
        m_exoPlayer?.run {
            prepare(mediaSource)
            playWhenReady = true
            addListener(eventListener)
            addAnalyticsListener(analyticsListener)
        }
        return true
    }

    fun stop()
    {
        m_exoPlayer?.let {
            it.stop()
            it.release()
        }
        m_exoPlayer = null
    }

    fun setVolume(fLeft : Float, fRight : Float)
    {
        m_exoPlayer?.let {
            it.volume = fLeft
        }
    }

    private fun bringToTitleListener(strUserAgent : String) : IcyHttpDataSourceFactory
    {
        val client = OkHttpClient.Builder().build()
        return IcyHttpDataSourceFactory.Builder(client)
            .setUserAgent(strUserAgent)
            .setIcyHeadersListener {
                Log.d("XXX", "onIcyHeaders: %s".format(it.toString()))
            }
            .setIcyMetadataChangeListener {
                m_strTitle = it.streamTitle
            }.build()
    }

    fun getTitle() : String
    {
        return m_strTitle
    }

    fun getSessionId() : Int
    {
        return m_exoPlayer!!.audioSessionId
    }

    val analyticsListener : AnalyticsListener = object : AnalyticsListener
    {
        override fun onAudioSessionId(eventTime: AnalyticsListener.EventTime?, audioSessionId: Int)
        {
            m_nSessionId = audioSessionId
        }
    }

    val eventListener: Player.EventListener = object : Player.EventListener
    {
        override fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {

        }

        override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray)
        {

        }

        override fun onLoadingChanged(isLoading: Boolean)
        {

        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            if (playWhenReady) {
                if (playbackState == ExoPlayer.STATE_READY) {

                }
            }
        }

        override fun onRepeatModeChanged(repeatMode: Int)
        {

        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean)
        {

        }

        override fun onPlayerError(error: ExoPlaybackException)
        {
            stop()
        }
        override fun onPositionDiscontinuity(reason: Int) {}
        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
        override fun onSeekProcessed() {}
    }
}