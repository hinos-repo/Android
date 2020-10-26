package com.mysample.exoIcyProject.lib

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log

class XwMediaPlayer : XwBasePlayer(XwBasePlayer.PLAYER_NORMAL) {

    internal var m_Player: MediaPlayer? = null
    var m_hPreparedListener: MediaPlayer.OnPreparedListener = MediaPlayer.OnPreparedListener { mp ->
        try {
            mp.start()
            m_hPlayResultListener?.onPlayResult(true)

            m_Player!!.setOnCompletionListener(m_hCompletionListener)
            state = XwBasePlayer.STATE_PLAY
        } catch (e: Exception) {
            Log.e(TAG, "onPrepared exception :" + e.message)
        }
    }
    internal var m_hErrorListener: MediaPlayer.OnErrorListener = MediaPlayer.OnErrorListener { mp, what, extra ->
        Log.d(TAG, "onError : what=$what, extra=$extra")

        if (state == XwBasePlayer.STATE_READY) {
            m_hPlayResultListener?.onPlayResult(false)
            stop()
        }
        true
    }
    internal var m_hInfoListener: MediaPlayer.OnInfoListener = MediaPlayer.OnInfoListener { mp, what, extra ->
        //@XwLog.i(TAG, "onInfo : what=" + what + ", extra=" + extra);
        false
    }
    internal var m_hCompletionListener: MediaPlayer.OnCompletionListener = MediaPlayer.OnCompletionListener {
        //@XwLog.e(TAG, "onCompletion");
        state = XwBasePlayer.STATE_STOP

        m_hPlayCompletionListener?.onPlayCompletion()
    }

    override fun play(context: Context, strUrl : String): Boolean
    {
        var nNewState = m_nState
        try {
            if (m_Player == null) {
                m_Player = MediaPlayer()

                // 2016.06.14 추가
                m_Player!!.setWakeMode(context, 1)
                m_Player!!.isLooping = false
                m_Player!!.setVolume(1.0f, 1.0f)
            }
            else {
                if (m_Player!!.isPlaying)
                {
                    m_Player!!.stop()
                    nNewState = XwBasePlayer.STATE_STOP
                }
                m_Player!!.reset()
            }


            // 2016.06.14 추가

            m_Player!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

            m_Player!!.setDataSource(strUrl)
            m_Player!!.setOnPreparedListener(m_hPreparedListener)
            m_Player!!.prepareAsync()
            m_Player!!.setOnErrorListener(m_hErrorListener)
            m_Player!!.setOnInfoListener(m_hInfoListener)

            state = XwBasePlayer.STATE_READY
        } catch (e: Exception) {
            Log.e(TAG, "play exception1 :" + e.message)
            try {
                if (m_Player != null) {
                    m_Player!!.pause()
                    m_Player!!.stop()
                    m_Player!!.reset()
                    m_Player!!.release()
                    m_Player = null
                    state = XwBasePlayer.STATE_STOP
                }
            } catch (e2: Exception) {
                Log.e(TAG, "play exception2 :" + e2.message)
            }

            return false
        }
        return true
    }

    override fun stop() {
        //		if(m_nState == STATE_STOP)
        //			return;

        try {
            if (m_Player != null) {
                m_Player!!.stop()
                m_Player!!.reset()
            }
        } catch (e: Exception) {
            Log.e(TAG, "stop exception :" + e.message)
        }

        state = XwBasePlayer.STATE_STOP
    }

    override fun release() {
        try {
            if (m_Player != null) {
                m_Player!!.pause()
                m_Player!!.stop()
                m_Player!!.reset()
                m_Player!!.release()
                m_Player = null
            }
        } catch (e: Exception) {
            Log.e(TAG, "release exception :" + e.message)
        }

        state = XwBasePlayer.STATE_STOP
    }

    override fun setVolume(left: Float, right: Float) {
        if (m_Player != null) {
            try {
                m_Player!!.setVolume(left, right)
            } catch (e: Exception) {
                Log.e(TAG, "setVolume exception : " + e.message)
            }

        }
    }

    companion object {
        private val TAG = XwMediaPlayer::class.java.simpleName
    }
}
