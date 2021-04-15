package com.example.mvpsample.system

import android.app.Application
import com.example.mvpsample.data.AppDatabase
import com.example.mvpsample.repo.CountRepository
import com.example.mvpsample.repo.CountRepositoryImpl

class MyApp : Application()
{
    lateinit var m_repository : CountRepository
    override fun onCreate()
    {
        super.onCreate()
        m_repository = CountRepositoryImpl(AppDatabase.getInstance(this))
    }
}