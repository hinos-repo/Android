package com.example.mvpsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.mvpsample.R
import com.example.mvpsample.contract.MainContract
import com.example.mvpsample.presenter.MainPresenter
import com.example.mvpsample.system.MyApp

class MainActivity : AppCompatActivity(),  MainContract.View
{
    private lateinit var m_presenter: MainContract.Presenter
    private lateinit var m_btnRequestData: Button
    private lateinit var m_app : MyApp
    private var m_toast : Toast? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        m_app = application as MyApp
        m_presenter = MainPresenter(this, m_app.m_repository)

        m_btnRequestData = findViewById(R.id.v_btnRequestData)
        m_btnRequestData.setOnClickListener {
            m_presenter.saveClick()
        }
    }



    override fun showMyToast(nCount: Int)
    {
        m_toast?.cancel()
        m_toast = Toast.makeText(this, "$nCount", Toast.LENGTH_SHORT)
        m_toast?.show()
    }
}