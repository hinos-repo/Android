package com.yklib.gmtproject

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun main()
{
//    val cal = Calendar.getInstance()
//    cal.time = Date()
//    val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//    df.timeZone = TimeZone.getTimeZone("GMT")

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