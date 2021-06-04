package com.example.tsdebugsample

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.os.Message
import android.util.Log
import java.lang.Exception

class XwMediaPlayer(context: Context, strPath: String)
{
    var m_context= context
    var m_strPath= strPath

    val TAG = XwMediaPlayer::class.java.simpleName
    var m_player: MediaPlayer = MediaPlayer()

    fun isPlaying(): Boolean
    {
        var bResult : Boolean
        try {
            bResult = m_player.isPlaying
        }
        catch (e : Exception)
        {
            bResult = false
            e.printStackTrace()
        }
        return bResult
    }

    fun play(): Boolean
    {
        var bResult = true
        try {
            m_player.reset()
            m_player.setDataSource(m_strPath)
            m_player.setAudioStreamType(AudioManager.STREAM_MUSIC)
            m_player.isLooping = true
            m_player.setOnErrorListener(onErrorListener)
            m_player.setOnPreparedListener(onPreparedListener)
            m_player.setOnCompletionListener(onCompleteionListner)
            m_player.prepare()

            setVolumePlayer(1.0f)
        }catch (e : Exception)
        {
            e.printStackTrace()
            bResult = false
        }
        return bResult
    }

    fun stop(): Boolean
    {
        var bResult = isPlaying()
        try {
            if(bResult)
            {
                m_player.stop()
                doStopPlayTime()
            }
        }
        catch (e : Exception)
        {
            bResult = false
        }
        return bResult
    }

    fun release(): Boolean
    {
        try {
            m_player.release()
        }
        catch (e : Exception)
        {
            return false
        }
        return true
    }

    fun pause(): Boolean
    {
        var bResult = true
        try {
            m_player.pause()
            doStopPlayTime()
        }
        catch (e : Exception)
        {
            bResult = false
        }
        return bResult
    }

    fun setVolumePlayer(fVolume: Float): Boolean
    {
        var bResult = true
        try {
            m_player.setVolume(fVolume, fVolume)
        }catch (e : Exception)
        {
            e.printStackTrace()
            bResult = false
        }
        return bResult
    }

    fun doPlayTime()
    {
        timeHandler.sendEmptyMessageDelayed(0, (1 * 1000).toLong())
    }

    private fun doStopPlayTime()
    {
        timeHandler.removeMessages(0)
    }

    var timeHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler()
    {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0 -> {
                    doPlayTime()
                }
            }
        }
    }

    var onPreparedListener: MediaPlayer.OnPreparedListener = MediaPlayer.OnPreparedListener{ mp ->
        mp.start()
        doPlayTime()
    }

    var onCompleteionListner: MediaPlayer.OnCompletionListener = MediaPlayer.OnCompletionListener { mp ->
        try {
            mp.release()
        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }

    var onErrorListener: MediaPlayer.OnErrorListener = MediaPlayer.OnErrorListener { mp, what, extra ->
        Log.d(TAG, "onError : what=$what, extra=$extra")
        try {
            mp.stop()
            mp.release()
        }catch (e : Exception)
        {
            e.printStackTrace()
        }
        doStopPlayTime()
        true
    }
}
