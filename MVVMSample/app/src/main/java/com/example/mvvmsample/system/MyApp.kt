package com.example.mvvmsample.system

import android.app.Application
import com.example.mvvmsample.data.AppDatabase
import com.example.mvvmsample.repo.CountRepository
import com.example.mvvmsample.repo.CountRepositoryImpl

class MyApp : Application()
{
    lateinit var m_repository : CountRepository
    override fun onCreate()
    {
        super.onCreate()
        m_repository = CountRepositoryImpl(AppDatabase.getInstance(this))
    }
}