package com.example.mvvmsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsample.R
import com.example.mvvmsample.viewmodel.MainViewModel
import com.example.mvvmsample.system.MyApp
import com.example.mvvmsample.viewmodel.factory.MainViewModelFactory

class MainActivity : AppCompatActivity()
{
    private lateinit var m_btnRequestData: Button
    private lateinit var m_app : MyApp
    private var m_toast : Toast? = null
    private lateinit var m_viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        m_app = application as MyApp
        m_btnRequestData = findViewById(R.id.v_btnRequestData)
        m_viewModel = ViewModelProvider(this, MainViewModelFactory(m_app.m_repository)).get(MainViewModel::class.java).apply {
            m_nCount.observe(this@MainActivity, Observer {
                showMyToast(it)
            })
            m_btnRequestData.setOnClickListener {
                m_viewModel.saveClick()
            }
        }
    }


    private fun showMyToast(nCount: Int)
    {
        m_toast?.cancel()
        m_toast = Toast.makeText(this, "$nCount", Toast.LENGTH_SHORT)
        m_toast?.show()
    }
}