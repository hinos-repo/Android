package com.mysample.exoeqtestproject

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.audiofx.AudioEffect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener
{
    companion object {
        val TESTURL = ""
    }

    val mPlayer : XwExoPlayer by lazy { XwExoPlayer(this) }
    val mEQFragment : EQFragment by lazy { EQFragment() }

    override fun onClick(v: View)
    {
        when(v.id)
        {
            R.id.btnPlay ->
            {
                showMessage("플레이")
                playRadio()
            }

            R.id.btnStop ->
            {
                stopRadio()
                showMessage("중지")
            }

            R.id.btnEQ ->
            {
//                val i = Intent()
//                i.putExtra(AudioEffect.EXTRA_PACKAGE_NAME, packageName)
//                i.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, mPlayer.getSessionId())
//                startActivity(i)
                if(!mEQFragment.bViewState)
                {
//                    requestPermission()
                    attachEQFragment()
                }
                else
                {
                    detachEQFragment()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewSetting()
    }

    private fun initViewSetting()
    {
        btnPlay.setOnClickListener(this)
        btnStop.setOnClickListener(this)
        btnEQ.setOnClickListener(this)
    }

    private fun showMessage(strMessage : String)
    {
        Toast.makeText(this, strMessage, Toast.LENGTH_SHORT).show()
    }

    private fun playRadio()
    {
        mPlayer.play(TESTURL)
        timeHandler.sendEmptyMessageDelayed(0,3 * 1000)
    }

    private fun stopRadio()
    {
        mPlayer.stop()
    }

    private var timeHandler: Handler = @SuppressLint("HandlerLeak") object :Handler()
    {
        override fun handleMessage(msg: Message)
        {
            if(mPlayer.getTitle().isNullOrEmpty())
            {
                sendEmptyMessageDelayed(0,3 * 1000)
            }
            else
            {
                tvTitle.text = mPlayer.getTitle()
            }
        }
    }

    fun attachEQFragment()
    {
        if(!mEQFragment.bViewState)
        {
            mEQFragment.setPlayer(mPlayer)
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

    private val REQUEST_RESULT = 55
    val arr_permission = listOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.MODIFY_AUDIO_SETTINGS)

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
}
