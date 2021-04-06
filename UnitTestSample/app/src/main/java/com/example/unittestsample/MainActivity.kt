package com.example.unittestsample

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.unittestsample.databinding.ActivityMainBinding
import org.jetbrains.annotations.TestOnly

class MainActivity : AppCompatActivity()
{
    private lateinit var m_binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        m_binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val arrTvList = createTextViewList(this@MainActivity, 50)
        m_binding.run {
            for (tv in arrTvList)
            {
                vLlTextContainer.addView(tv)
                vLlTextContainer.addView(createUnderLine(this@MainActivity))
            }
        }
    }

    fun createTextViewList(context : Context, nSize : Int) : List<TextView>
    {
        val arrTvList = mutableListOf<TextView>()
        for (i in 0..nSize)
        {
            val tv = TextView(context).apply {
                gravity = Gravity.CENTER
                textSize = 30f
                text = i.toString()
                tag = i
                setTextColor(Color.parseColor("#000000"))
            }
            arrTvList.add(tv)
        }
        return arrTvList
    }

    fun createUnderLine(context : Context) : View
    {
        return View(context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
            setBackgroundColor(Color.parseColor("#000000"))
        }
    }
}