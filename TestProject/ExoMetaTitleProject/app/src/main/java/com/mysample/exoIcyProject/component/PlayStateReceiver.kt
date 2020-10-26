package com.mysample.exoIcyProject.component


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

// PlayService 에서 통지하는 재생관련 상태 수신하는 Receiver
abstract class PlayStateReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent)
    {
        val strAct = intent.action

        try
        {
            when(strAct)
            {
                ACTION_TITLE_CHANGED    ->  // 서브 타이틀 바꾸기
                {
                    val strTitle = intent.getStringExtra(EXTRA_TITLE)
                    try {
                        onTitleChanged(strTitle)
                    } catch (e: Exception) {
                        Log.e(TAG, "title_action exception : " + e.message)
                    }
                }
            }
        } catch (e: Exception) {

        }

    }

    abstract fun onTitleChanged(strTitle: String)

    fun registerReceiver(context: Context)
    {
        var filter = IntentFilter(ACTION_STATE_CHANGED)

        context.registerReceiver(this, filter)

        filter = IntentFilter(ACTION_TITLE_CHANGED)
        context.registerReceiver(this, filter)
    }

    fun unregisterReceiver(context: Context)
    {
        context.unregisterReceiver(this)
    }

    companion object
    {
        val TAG = PlayStateReceiver::class.java.simpleName
        val PACKAGE_NAME = "com.mysample.exoIcyProject"
        val ACTION_STATE_CHANGED = PACKAGE_NAME + ".STATE_CHANGED"
        val ACTION_TITLE_CHANGED = PACKAGE_NAME + ".TITLE_CHANGED"
        val EXTRA_TITLE = "TITLE"

        fun sendTitle(context: Context, strTitle: String)
        {
            val i = Intent(ACTION_TITLE_CHANGED)

            i.putExtra(EXTRA_TITLE, strTitle)
            context.sendBroadcast(i)
        }
    }
}
