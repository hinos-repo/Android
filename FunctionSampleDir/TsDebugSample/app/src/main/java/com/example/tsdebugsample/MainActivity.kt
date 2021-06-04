package com.example.tsdebugsample

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity()
{
    val TAG = "main"

    private var mp1 : MediaPlayer? = null
    private var mp2 : MediaPlayer? = null
    private var mp3 : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mp1 = MediaPlayer.create(this, R.raw.a) // ts 파일 채널 1
        mp1?.setOnErrorListener { mediaPlayer, i, i2 ->
            Log.d(TAG, "onCreate: ")
            return@setOnErrorListener false
        }

        mp2 = MediaPlayer.create(this, R.raw.b) // ts 파일 채널 2
        mp2?.setOnErrorListener { mediaPlayer, i, i2 ->
            Log.d(TAG, "onCreate: ")
            return@setOnErrorListener false
        }

        mp3 = MediaPlayer.create(this, R.raw.c) // a 채널 2로 변경한 것
        mp3?.setOnErrorListener { mediaPlayer, i, i2 ->
            Log.d(TAG, "onCreate: ")
            return@setOnErrorListener false
        }
    }

    private fun test1(view: View)
    {
        mp1?.start()
    }
    private fun test2(view: View)
    {
        mp2?.start()
    }
    private fun test3(view: View)
    {
        mp3?.start()
    }
}