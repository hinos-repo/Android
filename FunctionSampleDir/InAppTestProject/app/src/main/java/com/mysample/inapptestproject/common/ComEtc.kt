package com.mysample.inapptestproject.common

import android.content.Context
import android.os.Environment
import java.io.File

object ComEtc
{
    fun makeHAHADATAFolder()
    {
        val f = File(Environment.getExternalStorageDirectory(), MyConfig.HAHA_FOLDER_NAME)
        if(!f.exists())
        {
            f.mkdir()
        }
    }

    fun getHAHADATAPath() : String
    {
        return File(Environment.getExternalStorageDirectory(), MyConfig.HAHA_FOLDER_NAME).path
    }

    fun getAppVersionNo(context: Context): Int
    {
        var nVer = 0
        try {
            val pi = context.packageManager.getPackageInfo(context.packageName, 0)

            return pi.versionCode
        } catch (e: Exception) {
            nVer = 0
        }

        return nVer
    }

    fun writeLog(strMessage : String)
    {
        val f = File(getHAHADATAPath(), "and_log.txt")
        f.printWriter().use {
            it.appendln(strMessage)
        }
    }
}