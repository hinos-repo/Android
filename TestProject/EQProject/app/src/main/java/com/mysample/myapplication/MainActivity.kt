package com.mysample.myapplication

import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.audiofx.Equalizer
import android.media.audiofx.Visualizer
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import java.util.jar.Manifest

class MainActivity : AppCompatActivity()
{
    val arr_permission = listOf(android.Manifest.permission.RECORD_AUDIO)
    val mEQFragment = EQFragment()
    var mPlayer : MediaPlayer? = null

    val TEST_URL = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPlay.setOnClickListener {
            mPlayer = MediaPlayer()
            mPlayer?.apply {
                setWakeMode(this@MainActivity, 1)
                isLooping = false
                setVolume(1.0f, 1.0f)
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(TEST_URL)
                setOnPreparedListener {
                    it.start()
                }
                prepareAsync()
            }
        }

        btnEQ.setOnClickListener {
            if(!mEQFragment.bViewState)
            {
                requestPermission()
                attachEQFragment()
            }
            else
            {
                detachEQFragment()
            }
        }
    }

    private val REQUEST_RESULT = 55

    fun requestPermission()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            val permissions = ArrayList<String>()
            for (permission in arr_permission)
            {
                var nPermissionCheck = checkSelfPermission(permission)
                if(nPermissionCheck != PackageManager.PERMISSION_GRANTED)
                {
                    permissions.add(permission)
                }
            }

            if(!permissions.isNullOrEmpty())
            {
                requestPermissions(permissions.toTypedArray(), REQUEST_RESULT)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        if(requestCode == REQUEST_RESULT)
        {
            if(grantResults.isNotEmpty() && grantResults.contains(PackageManager.PERMISSION_GRANTED))
            {
                attachEQFragment()
            }
        }
    }

    fun attachEQFragment()
    {
        if(!mEQFragment.bViewState)
        {
            mEQFragment.setPlayer(mPlayer!!)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.llEQContainer, mEQFragment).commit()
        }
    }

    fun detachEQFragment()
    {
        if(mEQFragment.bViewState)
        {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(mEQFragment).commit()
        }
    }
}
