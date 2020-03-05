package com.mysample.inapptestproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast

class PromotionReceiver : BroadcastReceiver()
{

    val ACTION = "com.android.vending.billing.PURCHASES_UPDATED"

    override fun onReceive(context: Context, intent: Intent)
    {
        val action = intent.action
        action?.let {
            if (it == ACTION)
            {
                Toast.makeText(context, "프로모션 결제 완료", Toast.LENGTH_SHORT).show()
                println("프로모션 결제 완료")
            }
        }
    }

    fun registerReceiver(context : Context)
    {
        val filter = IntentFilter(ACTION)
        context.registerReceiver(this, filter)
    }

    fun unregisterReceiver(context: Context)
    {
        context.unregisterReceiver(this)
    }
}
