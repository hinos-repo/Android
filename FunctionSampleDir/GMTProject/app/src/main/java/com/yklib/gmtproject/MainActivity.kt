package com.yklib.gmtproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputText = "Tue May 21 14:32:00 GMT 2012"
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT' yyyy", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("Etc/UTC")

        val outputFormat = SimpleDateFormat("MMM dd, yyyy h:mm a")
        // Adjust locale and zone appropriately
        // Adjust locale and zone appropriately
        val date = inputFormat.parse(inputText)
        val outputText = outputFormat.format(date)
        println(outputText)
    }
}