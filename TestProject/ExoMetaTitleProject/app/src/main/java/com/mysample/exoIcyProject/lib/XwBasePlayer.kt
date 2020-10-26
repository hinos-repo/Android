package com.mysample.exoIcyProject.lib

import android.content.Context


open class XwBasePlayer protected constructor(nType: Int) {

    var playerType = PLAYER_NORMAL
        protected set
    protected var m_nState = STATE_NONE
    protected var m_hStateChangedListener: OnStateChangedListener? = null
    protected var m_hPlayResultListener: OnPlayResultListener? = null
    protected var m_hPlayCompletionListener: OnPlayCompletionListener? = null
    var state: Int
        get() = m_nState
        protected set(nState) {
            if (m_nState == nState)
                return

            m_nState = nState
            if (m_hStateChangedListener != null) {
                m_hStateChangedListener!!.onChanged(nState)
            }
        }

    init {
        playerType = nType
    }

    fun loadUrl(strUrl: String) {}
    open fun play(context: Context, strUrl : String): Boolean {
        return false
    }

    open fun stop() {}
    open fun release() {}
    open fun setVolume(left: Float, right: Float) {}
    fun setOnStateChangedListener(h: OnStateChangedListener) {
        m_hStateChangedListener = h
    }

    fun setOnPlayResultListener(h: OnPlayResultListener) {
        m_hPlayResultListener = h
    }

    fun setOnPlayCompletionListener(h: OnPlayCompletionListener) {
        m_hPlayCompletionListener = h
    }

    interface OnStateChangedListener {
        fun onChanged(nState: Int)
    }

    interface OnPlayResultListener
    {
        fun onPlayResult(bSuccess: Boolean)
    }

    interface OnPlayCompletionListener {
        fun onPlayCompletion()
    }

    companion object {

        val PLAYER_NORMAL = 1
        val PLAYER_VITAMIO = 2
        val PLAYER_EXOPLAYER = 3

        val STATE_NONE = 1
        val STATE_READY = 2
        val STATE_PLAY = 3
        val STATE_STOP = 4
    }
}
