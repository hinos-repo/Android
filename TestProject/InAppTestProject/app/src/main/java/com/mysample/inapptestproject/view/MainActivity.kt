package com.mysample.inapptestproject.view

import android.Manifest
import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.anjlab.android.iab.v3.BillingProcessor
import com.karan.churi.PermissionManager.PermissionManager
import com.mysample.inapptestproject.ListAdapter
import com.mysample.inapptestproject.PromotionReceiver
import com.mysample.inapptestproject.R
import com.mysample.inapptestproject.bill.BillingModule
import com.mysample.inapptestproject.common.ComEtc
import com.mysample.inapptestproject.common.MyConfig
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URLEncoder
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener
{
    val arr_permission = mutableListOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

    val arrPurId = ArrayList<String>()
    val arrSubId = ArrayList<String>()

    lateinit var billingModule : BillingModule
    lateinit var billingProcessor : BillingProcessor

    val promotionReceiver by lazy {
        PromotionReceiver()
    }

    override fun onClick(v: View)
    {
        when(v.id)
        {
            R.id.btnAdsSub ->
            {
                if(!billingModule.isbListLoading())
                {
                    Toast.makeText(this, "리스트 로드 안됐다~", Toast.LENGTH_SHORT).show()
                    return
                }
                val skuDetailsList = billingModule.arr_whole
                if(!skuDetailsList.isNullOrEmpty())
                {
                    val adapter = ListAdapter(
                        this,
                        skuDetailsList,
                        billingModule
                    )
                    val layoutMgr = LinearLayoutManager(this)
                    recyclerView.layoutManager = layoutMgr
                    recyclerView.adapter = adapter
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }

            R.id.btnAdsOne ->
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    chooseAccountIntent()
                }
                else
                {
                    val strEmail = getUserEmail()
                }
            }

            R.id.btnPromo ->
            {
                val strUrl = MyConfig.CODE_URL + URLEncoder.encode(MyConfig.CODE_PR_ID, "UTF-8")
                val i = Intent(Intent.ACTION_VIEW)
                i.setPackage(MyConfig.MY_PACKAGE_NAME)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(strUrl)))
            }

            R.id.btnGetList ->
            {

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InitViewSetting()

        arrPurId.add(MyConfig.PRODUCT_ID)
        arrSubId.add(MyConfig.SUBSCRIBE_ID)

        billingModule = BillingModule(this, arrPurId,arrSubId)
        billingModule.initBillingProcessor()
        billingProcessor = billingModule.billingProcessor
        tvVersion.text = "버전 : ${ComEtc.getAppVersionNo(this)}"

        permission.checkAndRequestPermissions(this)
        promotionReceiver.registerReceiver(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        permission.checkResult(requestCode, permissions, grantResults)
        val granted = permission.status.get(0).granted
        val denied = permission.status.get(0).denied

        if(granted.size == arr_permission.size)
        {
            ComEtc.makeHAHADATAFolder()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (billingProcessor.handleActivityResult(requestCode, resultCode, data))
        {
            if (resultCode == RESULT_OK)
            {

            }
        }
        else if(requestCode == 55)
        {
            if (data != null)
            {
                val extaras = data.extras
                val email = extaras.getString(AccountManager.KEY_ACCOUNT_NAME)
                print(email)
            }
        }
    }

    // API Oreo
    fun getUserEmail() : String
    {
        val manager = AccountManager.get(this)
        val accounts = manager.getAccountsByType("com.google")
        val possibleEmails = LinkedList<String>()
        accounts.forEach {
            possibleEmails.add(it.name)
        }
        if (!possibleEmails.isNullOrEmpty())
            return possibleEmails[0]
        return ""
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun chooseAccountIntent()
    {
        val i = AccountManager.newChooseAccountIntent(
            null, null, arrayOf("com.google"),
            null, null, null, null
        )
        startActivityForResult(i, 99)
    }

    val permission = object: PermissionManager()
    {
        override fun ifCancelledAndCanRequest(activity: Activity?)
        {
            super.ifCancelledAndCanRequest(activity)
        }

        override fun ifCancelledAndCannotRequest(activity: Activity?)
        {
            super.ifCancelledAndCannotRequest(activity)
        }
        override fun setPermission(): MutableList<String>
        {
            return arr_permission
        }
    }

    fun InitViewSetting()
    {
        btnAdsSub.setOnClickListener(this)
        btnAdsOne.setOnClickListener(this)
        btnPromo.setOnClickListener(this)
        btnGetList.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        billingModule.releaseBillingProcessor()
        promotionReceiver.unregisterReceiver(this)
    }
}
