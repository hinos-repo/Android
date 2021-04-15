package com.example.clickviewsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val v_icv = findViewById<IconClickView>(R.id.v_icv)
        v_icv.setOnClickListener {
            showMyToast("클릭 영역이 늘어났나?")
        }
    }

    private fun showMyToast(strMessage: String)
    {
        Toast.makeText(this, strMessage, Toast.LENGTH_SHORT).show()
    }
}