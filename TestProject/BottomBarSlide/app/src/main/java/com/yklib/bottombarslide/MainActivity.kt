package com.yklib.bottombarslide

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.v_btnMain)
        btn.setOnClickListener {
            Toast.makeText(this, "메인 버튼", Toast.LENGTH_SHORT).show()
        }

        val tv = findViewById<TextView>(R.id.v_tvTest)
        tv.setOnClickListener {
            Toast.makeText(this, "메인 텍스트", Toast.LENGTH_SHORT).show()
        }

        val v_con2 = findViewById<LinearLayout>(R.id.v_con2)


        val metric = this.applicationContext.resources.displayMetrics
        val height = (metric.heightPixels / 3)

        val sp = findViewById<SlidingUpPanelLayout>(R.id.v_sp)
        sp.panelHeight = height

        val lp = SlidingUpPanelLayout.LayoutParams(SlidingUpPanelLayout.LayoutParams.MATCH_PARENT, SlidingUpPanelLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(0, height, 0, 0)
        v_con2.layoutParams = lp
    }
}