package com.mysample.myapplication

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.media.audiofx.Equalizer
import android.media.audiofx.Visualizer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_eq.*


class EQFragment : Fragment()
{
    var bViewState = false
    var mPlayer : MediaPlayer? = null

    lateinit var mVisualizer : Visualizer
    lateinit var mEqualizer: Equalizer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        mEqualizer = Equalizer(0, mPlayer!!.audioSessionId)
        mEqualizer.enabled = true

        val bands = mEqualizer.numberOfBands

        val minEQLevel = mEqualizer.bandLevelRange[0]
        val maxEQLevel = mEqualizer.bandLevelRange[1]

        val arrTvHz = listOf<TextView>(tvHz1, tvHz2, tvHz3, tvHz4, tvHz5)
        val arrVerSb = listOf<VerticalSeekBar>(verticalSeekBar1, verticalSeekBar2, verticalSeekBar3, verticalSeekBar4, verticalSeekBar5)

        for(i in 0 until bands)
        {
            val band = i.toShort()
            arrTvHz[i].text = "${mEqualizer.getCenterFreq(band) / 1000}Hz"
            arrVerSb[i].max = maxEQLevel - minEQLevel
            arrVerSb[i].progress = mEqualizer.getBandLevel(band).toInt()

            arrVerSb[i].setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
                {
                    mEqualizer.setBandLevel(band, (progress + minEQLevel).toShort())
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val v = inflater.inflate(R.layout.fragment_eq, container, false)
        return v
    }

    fun setPlayer(player : MediaPlayer)
    {
        mPlayer = player
    }
    override fun onDetach()
    {
        super.onDetach()
        bViewState = false
    }
}
