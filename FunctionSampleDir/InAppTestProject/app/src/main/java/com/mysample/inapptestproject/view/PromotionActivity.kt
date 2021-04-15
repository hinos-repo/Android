package com.mysample.inapptestproject.view

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mysample.inapptestproject.PromotionReceiver
import com.mysample.inapptestproject.R
import com.mysample.inapptestproject.common.MyConfig
import kotlinx.android.synthetic.main.activity_promotion.*
import java.net.URLEncoder

class PromotionActivity : AppCompatActivity() {
    val promotionReceiver by lazy {
        PromotionReceiver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotion)
        promotionReceiver.registerReceiver(this)
        editText.setText(MyConfig.CODE_PR_ID)

        btnUse.setOnClickListener {
            val strInput = editText.text.toString()
            if (strInput.isNullOrEmpty())
            {
                Toast.makeText(this, "프로모션 코드를 적어주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val strUrl = MyConfig.CODE_URL + URLEncoder.encode(editText.text.toString(), "UTF-8")
            val i = Intent(Intent.ACTION_VIEW)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(strUrl)))


//            NetRetrofit.instance.service.sendPromotion(strUrl).enqueue(object : Callback<JsonObject>{
//                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//                    Toast.makeText(this@PromotionActivity, "실패", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
//                    print("response ${response.body()}" )
//                    Toast.makeText(this@PromotionActivity, "굿", Toast.LENGTH_SHORT).show()
//                }
//
//            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        promotionReceiver.unregisterReceiver(this)
    }
}
