package com.example.capturesample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.text.format.DateFormat
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capturesample.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.util.*


class MainActivity : AppCompatActivity()
{
    private val TAG = MainActivity::class.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addDummyView()

        binding.vBtnCapture.setOnClickListener {
            val bResult = ComPermission.getPermissionState(this, ComPermission.EnumPermission.STORAGE_PERMISSION)
            if(bResult)
            {
                if (takeScreenshot()) showMyToast(this, "캡처 완료") else showMyToast(this,"캡처 실패")
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        val bResult = ComPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, ComPermission.EnumPermission.STORAGE_PERMISSION)
        if (bResult)
        {
            // 유저가 모든 권한을 승인했을 때
            if (takeScreenshot()) showMyToast(this, "캡처 완료") else showMyToast(this,"캡처 실패")
        }
        else
        {
            showMyToast(this, "권한을 취소하여 캡처할 수 없어용")
        }
    }

    private fun addDummyView()
    {
        for(i in 0..1000)
        {
            val tv = TextView(this)
            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            tv.layoutParams = lp
            tv.textSize = 25f
            tv.text = i.toString()
            binding.vLlScroll.addView(tv)
        }
    }

    private fun takeScreenshot() : Boolean
    {
        val now = Date()
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
        try {
            val view = window.decorView.rootView

            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val bgDrawable = view.background
            if (bgDrawable != null) bgDrawable.draw(canvas)
            else canvas.drawColor(Color.WHITE)
            view.draw(canvas)

            // image naming and path  to include sd card  appending name you choose for file
            val strPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg"
            Log.d(TAG, "takeScreenshot: $strPath")
            val imageFile = File(strPath)
            val outputStream = FileOutputStream(imageFile)
            val nQuality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, nQuality, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: Throwable) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun showMyToast(context : Context, strMessage : String)
    {
        Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show()
    }
}