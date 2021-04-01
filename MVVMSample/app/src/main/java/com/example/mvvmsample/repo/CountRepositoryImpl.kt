package com.example.mvvmsample.repo

import androidx.lifecycle.LiveData
import com.example.mvvmsample.data.AppDatabase
import com.example.mvvmsample.data.ClickVo

class CountRepositoryImpl(
    private val m_database : AppDatabase
) : CountRepository
{

    override suspend fun saveClick()
    {
        m_database.daoClick().insert(ClickVo())
    }

    override suspend fun getClickCount() : Int
    {
        return m_database.daoClick().getCount()
    }
}