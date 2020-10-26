package com.mysample.exoIcyProject.component

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.mysample.exoIcyProject.R
import com.mysample.exoIcyProject.lib.XwMediaPlayer
import com.mysample.exoIcyProject.lib.XwExoPlayer
import com.mysample.exoIcyProject.lib.XwIcyExoPlayer
import kotlinx.android.synthetic.main.activity_base.*


class BaseActivity : AppCompatActivity()
{
    val STATIC_URL = "http://45.32.61.6:8001"
    val XwIcyExoPlayer by lazy { XwIcyExoPlayer(this) }
    val XwExoPlayer by lazy { XwExoPlayer(this) }
    val XwDefaultMPlayer by lazy { XwMediaPlayer() }

    var bIcyExoPlayCheck = false
    var bNormalExoPlayCheck = false
    var bWithPlayCheck = false
    var bMediaPlayCheck = false

    private lateinit var m_StateReceiver    : StateReceiver

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        m_StateReceiver = StateReceiver()
        m_StateReceiver.registerReceiver(this)

        btnExo.setOnClickListener { playOrStopExoPlayer() }
        btnMedia.setOnClickListener { playOrStopMediaPlayers() }
        btnWith.setOnClickListener { playOrStopDual() }
        btnTextClear.setOnClickListener { clearText() }
        btnNormalExo.setOnClickListener { playOrStopNormalExoPlayer() }
    }

    fun playOrStopNormalExoPlayer()
    {
        if (!bNormalExoPlayCheck)
        {
            XwExoPlayer.stop()
            XwExoPlayer.play(this, STATIC_URL)
        }
        else
        {
            XwExoPlayer.stop()
            XwExoPlayer.release()
        }
        bNormalExoPlayCheck = !bNormalExoPlayCheck
    }

    fun playOrStopMediaPlayers()
    {
        if (!bWithPlayCheck)
        {
            XwIcyExoPlayer.stop()
            XwDefaultMPlayer.stop()
            XwIcyExoPlayer.play(this, STATIC_URL)
            XwDefaultMPlayer.play(this, STATIC_URL)
        }
        else
        {
            XwIcyExoPlayer.stop()
            XwIcyExoPlayer.release()
            XwDefaultMPlayer.stop()
            XwDefaultMPlayer.release()
        }
        bWithPlayCheck = !bWithPlayCheck
    }

    fun playOrStopExoPlayer()
    {
        if (!bIcyExoPlayCheck)
        {
            XwIcyExoPlayer.stop()
            XwIcyExoPlayer.play(this, STATIC_URL)
        }
        else
        {
            XwIcyExoPlayer.stop()
            XwIcyExoPlayer.release()
        }
        bIcyExoPlayCheck = !bIcyExoPlayCheck
    }

    fun playOrStopDual()
    {
        if (!bMediaPlayCheck)
        {
            XwDefaultMPlayer.stop()
            XwDefaultMPlayer.play(this, STATIC_URL)
        }
        else
        {
            XwDefaultMPlayer.stop()
            XwDefaultMPlayer.release()
        }
        bMediaPlayCheck = !bMediaPlayCheck
    }

    fun clearText()
    {
        tvTitle.post(Runnable {

        })
        tvTitle.text = ""
    }

    internal inner class StateReceiver : PlayStateReceiver()
    {
        override fun onTitleChanged(strTitle: String)
        {
            tvTitle.text = strTitle
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        m_StateReceiver.unregisterReceiver(this)
    }
}